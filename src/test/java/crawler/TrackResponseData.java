package crawler;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author:ben.gu
 * @Date:2019/5/8 2:39 PM
 */
public class TrackResponseData {
    String status;

    String message;

    Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    static class Entity implements Comparable<Entity> {
        String category;

        String name;

        int rank;

        String cover;

        String url;

        String unique ;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUnique() {
            return unique;
        }

        public void setUnique(String unique) {
            this.unique = unique;
        }

        @Override
        public int compareTo(Entity o) {
            if (o == null) {
                return -1;
            }
            if (o == this) {
                return 0;
            }

            return this.rank - o.rank;
        }
    }

    class Result {
        int total;

        boolean hasPrevious;

        boolean hasNext;

        List<Entity> entities;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isHasPrevious() {
            return hasPrevious;
        }

        public void setHasPrevious(boolean hasPrevious) {
            this.hasPrevious = hasPrevious;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public void setEntities(List<Entity> entities) {
            this.entities = entities;
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
