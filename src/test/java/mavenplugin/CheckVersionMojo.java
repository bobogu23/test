package mavenplugin;

import java.util.*;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.artifact.filter.collection.ArtifactFilterException;
import org.apache.maven.shared.artifact.filter.collection.FilterArtifacts;

/**
 * @author:ben.gu
 * @Date:2018/12/13 2:19 PM
 * @goal check
 * @phase process-resources
 * @requiresDependencyResolution  compile
 * 使用方式:
 * 目标项目pom文件 plugin中加入maven依赖
 * executions 中加入phase: process-resources,goal:check
 */
@Mojo(name = "versionCheck")
public class CheckVersionMojo extends AbstractMojo {

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * 插件参数
     * @parameter expression="${checklist}"
     * @required
     */
    private String checklist;




    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("checkList := " + checklist);
        if (this.project == null) {
            throw new MojoExecutionException("Can not find a Maven project.");
        }

        if (("".equals(this.checklist)) || (null == this.checklist)) {
            getLog().info("Checklist is empty, bye.");
            return;
        }

        getLog().debug("checklist:" + this.checklist);


        Map<ArtifactPojo,Artifact> brokenArtifacts = getBrokenArtifacts();
        Artifact artifact = null;

        getLog().debug("The count of invalid artifacts: " + brokenArtifacts.size());

        if (!brokenArtifacts.isEmpty()) {
            getLog().info("The version of following files are invalid.");
            Set<ArtifactPojo> brokenArtifactsSet = brokenArtifacts.keySet();
            for (ArtifactPojo sa : brokenArtifactsSet) {
                artifact = (Artifact)brokenArtifacts.get(sa);
                getLog().error(sa.getGroupId() + ":" + sa.getArtifactId() + " required version[" + sa.getVersion() + "],or higher" +
                        " actual version[" + artifact.getVersion() + "]");
            }
            throw new MojoFailureException("please upgrade to the specified version.");
        }
    }



    private Set<ArtifactPojo> fromChecklist(String checklist) {
        Set simpleArtifacts = new HashSet();
        String[] toBeChecked = checklist.split(",");
        String[] splitedArray = null;
        for (int i = 0; i < toBeChecked.length; i++) {
            splitedArray = toBeChecked[i].split(":");
            if (splitedArray.length < 3)
                getLog().warn("Invalid artifact: " + toBeChecked[i] + ", available format is groupId:artifactId:verion");
            else {
                simpleArtifacts.add(new ArtifactPojo(splitedArray[0], splitedArray[1], splitedArray[2]));
            }
        }
        return simpleArtifacts;
    }

    private Map<ArtifactPojo, Artifact> getBrokenArtifacts() throws MojoExecutionException
    {
        Set simpleArtifacts = fromChecklist(this.checklist);
        Set artifacts = this.project.getArtifacts();
        System.out.println(artifacts);
        FilterArtifacts filter = new FilterArtifacts();
        filter.clearFilters();
        Set unMarkedArtifacts = null;
        try {
            unMarkedArtifacts = filter.filter(artifacts);
        } catch (ArtifactFilterException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        ArtifactPojo ArtifactPojo = null;
        Artifact artifact = null;

        Iterator it;
        Map brokenArtifacts = new HashMap();
        for (Iterator sait = simpleArtifacts.iterator(); sait.hasNext(); ) {
            ArtifactPojo = (ArtifactPojo)sait.next();
            for (it = unMarkedArtifacts.iterator(); it.hasNext(); ) {
                artifact = (Artifact)it.next();
                if (ArtifactPojo.compareTo(artifact) > 0)
                    brokenArtifacts.put(ArtifactPojo, artifact);
            }
        }

        return brokenArtifacts;
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }
}
