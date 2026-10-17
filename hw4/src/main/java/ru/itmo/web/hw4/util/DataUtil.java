package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.RED),
            new User(6, "pashka", "Pavel Mavrin", User.Color.GREEN),
            new User(9, "geranazavr555", "Georgiy Nazarov", User.Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Первый длинный пост", "Здравствуйте, уважаемые господа, меня зовут Игорь Гофман, я живу в городе Херсоне, Украина. Хочу кое-что интересное рассказать. Значит, начались эти события... Ну, начались они вообще с моего практически рождения, там очень много различных предпосылок было. Но, в частности, вот, начались эти события с тако... с такой ситуации. Значит, была у нас когда-то дверь, которая стояла ещё при Советском Союзе, мы когда-то её сняли, заменили металлической, поставили на балкон. Позвонил нам один азербайджанец, знакомый, который видел её... ну, снизу, с улицы видел, что дверь стоит, захотел её купить. Позвонил: \"Я куплю у вас дверь\". \"Хорошо, хорошо,\" – потом через некоторое время, у, в принципе... ну, не особенно... просто, ну, забыли про эту дверь. Значит, потом однажды ночью мне, ну, наитие такое появилось, что, вот, мне нужно выполнить определённую... как бы, определённый алгоритм. Я лёг на диване, поднял ноги, ну, вот, на столе, поднял ноги и положил на стол, и открыл окно. У меня было такие мысли, что есть такой у меня, так сказать, знакомый... Ну, я его знаю, например, как Евгений Дмитриевич Метила. Но, в частности, я его когда видел, ну, есть такие понятия преобразование внешности...", 1),
            new Post(2, "Второй длинный пост", "Съешь ещё этих мягких французских булочек, да выпей же чаю. The quick brown fox jumps over the lazy dog. ", 6),
            new Post(3, "Lorem", "Ipsum Dolor", 9)
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
