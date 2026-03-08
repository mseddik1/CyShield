package apis.models;

import java.util.List;

public class Posts {


    private List<Post> posts;
    private Integer total;
    private Integer skip;
    private Integer limit;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }





    public static class Post{
        private Integer id;
        private String title;
        private String body;
        private List<String> tags;
        private Reactions reactions;
        private Integer views;
        private Integer userId;



        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Reactions getReactions() {
            return reactions;
        }

        public void setReactions(Reactions reactions) {
            this.reactions = reactions;
        }

        public Integer getViews() {
            return views;
        }

        public void setViews(Integer views) {
            this.views = views;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

    public static class Reactions {

        private Integer likes;
        private Integer dislikes;

        public Integer getLikes() {
            return likes;
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        public Integer getDislikes() {
            return dislikes;
        }

        public void setDislikes(Integer dislikes) {
            this.dislikes = dislikes;
        }
    }


}
