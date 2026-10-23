package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov"),
            new User(6, "pashka", "Pavel Mavrin"),
            new User(9, "geranazavr555", "Georgiy Nazarov"),
            new User(11, "tourist", "Gennady Korotkevich")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "qwe", "Lorem1 ipsum dolor sit amet, consectetur adipisicing elit. A ab aliquam dignissimos distinctio, eius, eum eveniet exercitationem illo minima minus molestiae molestias odio officia officiis optio perspiciatis quae quia quibusdam ratione rem sint temporibus veniam? Accusamus dignissimos dolorum earum id labore, nostrum provident sit voluptates. Alias asperiores, aut cupiditate debitis iure iusto modi molestiae, nostrum officia pariatur quidem quisquam, tempore unde! Adipisci amet autem culpa delectus, dolorum eaque est eum expedita fuga ipsum labore modi molestias nostrum numquam perspiciatis repellendus sunt, veniam! Aliquam at deleniti enim eum facere, facilis, labore nisi, non praesentium quibusdam quis recusandae? At dicta distinctio explicabo?", 11),
            new Post(2, "asd", "Lorem2 ipsum dolor sit amet, consectetur adipisicing elit. Illo, possimus!", 11),
            new Post(3, "zxc", "Lorem3 ipsum dolor sit amet, consectetur adipisicing elit. Illo, possimus!", 6),
            new Post(4, "qwerty", "Lorem4 ipsum dolor sit amet, consectetur adipisicing elit. Illo, possimus!", 1),
            new Post(5, "asdfgh", "Lorem5 ipsum dolor sit amet, consectetur adipisicing elit. Illo, possimus!", 9)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
