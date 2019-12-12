package mavenplugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;
import org.apache.maven.shared.dependency.tree.traversal.CollectingDependencyNodeVisitor;

/**
 * @author:ben.gu
 * @Date:2019/2/21 6:23 PM
 *  @goal log4j2Check
 * @phase process-resources
 * @requiresDependencyResolution compile
 */
@Mojo(name = "checkLog4j2") public class CheckLog4j2Mojo extends AbstractMojo {

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The artifact factory to use.
     *
     * @component
     * @required
     * @readonly
     */
    private ArtifactFactory artifactFactory;

    /**
     * The artifact metadata source to use.
     *
     * @component
     * @required
     * @readonly
     */
    private ArtifactMetadataSource artifactMetadataSource;

    /**
     * The artifact collector to use.
     *
     * @component
     * @required
     * @readonly
     */
    private ArtifactCollector artifactCollector;

    /**
     * The dependency tree builder to use.
     *
     * @component
     * @required
     * @readonly
     */

    private DependencyTreeBuilder treeBuilder;

    /**
     * The artifact repository to use.
     *
     * @parameter expression="${localRepository}"
     * @required
     * @readonly
     */
    private ArtifactRepository localRepository;

    @Override public void execute() throws MojoExecutionException, MojoFailureException {

        List<Artifact> brokenArtifacts = getLog4j2Artifacts();

        getLog().debug("The num of log4j2 artifacts: " + brokenArtifacts.size());
        Collections.sort(brokenArtifacts);

        if (!brokenArtifacts.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Artifact sa : brokenArtifacts) {
                stringBuilder.append(sa.getGroupId()).append(":").append(sa.getArtifactId()).append(":")
                        .append(sa.getVersion());
                stringBuilder.append("\r\n");
            }
            throw new MojoFailureException(
                    "detect jar name of log4j2!!! please exclude them. following are jar names : \r\n" + stringBuilder.toString());
        }
    }

    private List<Artifact> getLog4j2Artifacts() throws MojoExecutionException {

        List brokenArtifacts = new ArrayList();

        ArtifactFilter artifactFilter = new ScopeArtifactFilter(null);

        DependencyNode rootNode = null;
        try {
            rootNode = treeBuilder
                    .buildDependencyTree(project, localRepository, artifactFactory, artifactMetadataSource,
                            artifactFilter, artifactCollector);
            CollectingDependencyNodeVisitor visitor = new CollectingDependencyNodeVisitor();

            rootNode.accept(visitor);

            List<DependencyNode> nodes = visitor.getNodes();

            for (DependencyNode dependencyNode : nodes) {
                Artifact artifact = dependencyNode.getArtifact();
                String artifactId = artifact.getArtifactId();
                if (artifactId.contains("log4j2")) {
                    brokenArtifacts.add(artifact);
                }

                List<DependencyNode> childrens = dependencyNode.getChildren();
                if (childrens != null && childrens.size() > 0) {
                    for (DependencyNode childArtifact : childrens) {
                        if (childArtifact.getArtifact().getArtifactId().contains("log4j2")) {
                            brokenArtifacts.add(artifact);
                        }
                    }
                }

            }

        } catch (DependencyTreeBuilderException e) {
            getLog().error("DependencyTreeBuilderException:" + e.getMessage());
        }

        return brokenArtifacts;

    }
}
