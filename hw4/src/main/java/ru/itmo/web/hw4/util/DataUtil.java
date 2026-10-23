package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Color;
import ru.itmo.web.hw4.model.User;
import ru.itmo.web.hw4.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class DataUtil {
    private final static String TEST_GEN_TEXT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu";

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", Color.RED),
            new User(9, "geranazavr555", "Georgiy Nazarov", Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "first post!", "nothing", 1),
            new Post(4, "second post", TEST_GEN_TEXT, 6),
            new Post(6, "third post", "privet", 11),
            new Post(7, "fourth post", TEST_GEN_TEXT, 11),
            new Post(232, "no user_id post", "bebee", 32)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);

        List<Post> reversedPosts = new ArrayList<>(POSTS);
        Collections.reverse(reversedPosts);
        data.put("posts", reversedPosts);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }

    public static void addNeighbors(HttpServletRequest request, Map<String, Object> data) {
        String handle = request.getParameter("handle");
        String userId = request.getParameter("user_id");

        for (int i = 0; i < USERS.size(); i++) {
            User currUser = USERS.get(i);
            if ((handle != null && handle.equals(currUser.getHandle()))
                    || (userId != null && userId.equals(Long.toString(currUser.getId())))) {
                data.put("currUser", currUser);
                int postsCnt = 0;
                for (Post post: POSTS){
                    if (post.getUser_id() == currUser.getId()){
                        postsCnt++;
                    }
                }
                data.put("postCnt", postsCnt);
                if (i > 0) {
                    data.put("prevUser", USERS.get(i - 1));
                }
                if (i < USERS.size() - 1) {
                    data.put("nextUser", USERS.get(i + 1));
                }
                return;
            }
        }
    }

    public static void addPost(HttpServletRequest request, Map<String, Object> data) {
        String id = request.getParameter("post_id");

        for (Post post: POSTS){
            if (Long.toString(post.getId()).equals(id)){
                data.put("currPost", post);
                return;
            }
        }
    }

    public static void addUserPosts(HttpServletRequest request, Map<String, Object> data){
        String user_id = request.getParameter("user_id");

        List<Post> userPosts = new ArrayList<>();
        for (Post post: POSTS){
            if (Long.toString(post.getUser_id()).equals(user_id)){
                userPosts.add(post);
            }
        }
        Collections.reverse(userPosts);
        data.put("user_posts", userPosts);
    }
}
