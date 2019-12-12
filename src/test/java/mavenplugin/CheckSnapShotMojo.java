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
 * @Date:2019/2/21 6:23 PM
 *  @goal showSnapshot
 * @phase process-resources
 * @requiresDependencyResolution compile
 */
@Mojo(name = "snapshotCheck")
public class CheckSnapShotMojo extends AbstractMojo {

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * 插件参数
     * @parameter expression="${checkSnapshot}" default-value=true
     * @required
     */
    private boolean isCheck;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if(!isCheck){
            return;
        }
        List<Artifact> brokenArtifacts = getSnapshotArtifacts();

        getLog().debug("The count of snapshot artifacts: " + brokenArtifacts.size());
        Collections.sort(brokenArtifacts);

        if (!brokenArtifacts.isEmpty()) {
            getLog().info("The following jars are snapshot version...");
            for (Artifact sa : brokenArtifacts) {
                getLog().error(sa.getGroupId() + ":" + sa.getArtifactId() + ":" + sa.getVersion() );
            }
            throw new MojoFailureException("please upgrade to the specified version.");
        }
    }

    private List<Artifact> getSnapshotArtifacts() throws MojoExecutionException {
        //取不到间接依赖的jar，可以参照CheckLog4j2Mojo 类中的写法
        Set artifacts = this.project.getArtifacts();
        FilterArtifacts filter = new FilterArtifacts();
        filter.clearFilters();
        Set unMarkedArtifacts;
        try {
            unMarkedArtifacts = filter.filter(artifacts);
        } catch (ArtifactFilterException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        Artifact artifact;
        Iterator it;
        List brokenArtifacts = new ArrayList();

        for (it = unMarkedArtifacts.iterator(); it.hasNext(); ) {
            artifact = (Artifact) it.next();
            if (artifact.isSnapshot()) {
                brokenArtifacts.add(artifact);
            }
        }

        return brokenArtifacts;
    }
}
