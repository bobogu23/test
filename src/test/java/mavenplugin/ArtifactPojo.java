package mavenplugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * @author:ben.gu
 * @Date:2018/12/13 2:12 PM
 */
public class ArtifactPojo implements Comparable<Artifact> {

    private String groupId;
    private String artifactId;
    private String version;

    public ArtifactPojo(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public ArtifactPojo() {
    }

    @Override
    public int compareTo(Artifact artifact)
    {
        if ((artifact != null) && (getGroupId().equals(artifact.getGroupId())) &&
                (getArtifactId().equals(artifact.getArtifactId())))
        {
            return new DefaultArtifactVersion(getVersion()).compareTo(new DefaultArtifactVersion(artifact.getVersion()));
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtifactPojo that = (ArtifactPojo) o;

        if (!artifactId.equals(that.artifactId)) return false;
        if (!groupId.equals(that.groupId)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + artifactId.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    @Override
    public String toString()
    {
        return this.groupId + ":" + this.artifactId + ":" + this.version;
    }

    public boolean isSnapShot(){
        return version.toLowerCase().contains("snapshot");
    }
}
