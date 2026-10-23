package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", User.Color.GREEN),
            new User(9, "geranazavr555", "Georgiy Nazarov", User.Color.RED),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.BLUE)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "New constest soon", "We wait you for new contest", 1),
            new Post(4, "My first post", "Hi, it's my first post, good luck))))", 9),
            new Post(9, "<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, consequatur, quod! Aperiam consectetur consequatur cupiditate dolore, doloribus est eveniet hic laborum laudantium libero maxime minima molestias nam necessitatibus quae reiciendis sit tempore ullam. Aliquam earum eius fugit nisi quae. Consectetur delectus dicta et harum molestias pariatur sed sit vel voluptate.</p>", "<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur atque cupiditate debitis deleniti eveniet, fugiat ipsum magnam minima perferendis quia quis recusandae. Accusamus adipisci amet asperiores aut, autem blanditiis consequatur dignissimos dolores doloribus dolorum eaque eius facilis fugit ipsam magnam mollitia nam! Ad debitis dolorum harum, illo impedit itaque iure iusto, maxime nesciunt nisi quaerat quis ratione rerum sit voluptatum. Adipisci animi asperiores aspernatur autem commodi cum cumque dicta distinctio dolor dolorum eligendi eum eveniet hic illo ipsa ipsum laudantium modi nam nisi obcaecati officia placeat repellat reprehenderit similique sint soluta tenetur ut, voluptas voluptatem voluptatibus. Aliquam assumenda dolores, eius iusto laborum magnam nobis officiis sit soluta vel. A ad amet asperiores aspernatur consectetur deleniti dolor dolorem et expedita facere, hic id illum in inventore ipsa itaque laudantium mollitia nesciunt officiis perspiciatis quibusdam quod quos rerum soluta temporibus! At cum distinctio, impedit ratione repellat rerum unde? Amet corporis deleniti dolore doloribus eaque quam sed tempora unde! Accusantium alias animi asperiores aut autem corporis culpa dignissimos distinctio dolor dolorum enim fugiat ipsa magnam nemo numquam obcaecati optio porro quidem quo sequi sunt suscipit tempore unde vel velit, voluptates voluptatum? Architecto dignissimos iste magnam modi mollitia, omnis perspiciatis quam rem sit ut? Accusamus animi cum deleniti dignissimos explicabo fugit incidunt ipsa molestias nam nesciunt nobis, quasi quidem quis repellendus sed tenetur ut? A ab blanditiis cum dicta exercitationem mollitia odio praesentium temporibus ut. Accusamus ad alias corporis debitis doloremque enim, et fugiat fugit magni necessitatibus non placeat quasi quos ratione similique, vel voluptatem voluptatibus. Adipisci autem maiores perferendis. Amet debitis doloremque excepturi, hic nam numquam sed soluta. Animi dolor doloribus eius esse explicabo facere impedit, maxime obcaecati odio quae quaerat, qui? Ab commodi, delectus eaque eligendi et eveniet expedita inventore ipsam labore laboriosam magnam natus nemo officia quaerat sint vitae voluptatem.</p>", 11)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("logged_user", user);
            }
        }
    }
}
