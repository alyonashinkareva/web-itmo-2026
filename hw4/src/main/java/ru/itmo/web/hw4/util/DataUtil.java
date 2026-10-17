package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.itmo.web.hw4.model.Color.*;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", RED),
            new User(6, "pashka", "Pavel Mavrin", GREEN),
            new User(9, "geranazavr555", "Georgiy Nazarov", GREEN),
            new User(11, "tourist", "Gennady Korotkevich", BLUE)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "MikeMirzayanov", "Hello, Codeforces", 1),
            new Post(2, "pashka", "Codeforces Round #510 (Div. 2) will start at Monday, September 17, 2018 at 11:05. The round will be\n" +
                    "                    rated for Div. 2 contestants (participants with the rating below 2100). Div. 1 participants can take\n" +
                    "                    a part out of competition as usual.", 6),
            new Post(3, "geranazavr555", "This round is held on the tasks of the school stage All-Russian Olympiad of Informatics 2018/2019\n" +
                    "                    year in city Saratov. The problems were prepared by PikMike, fcspartakm, Ne0n25, BledDest, Ajosteen\n" +
                    "                    and Vovuh. Great thanks to our coordinator _kun_ for the help with the round preparation! I also\n" +
                    "                    would like to thank our testers DavidDenisov, PrianishnikovaRina, Decibit and Vshining.", 9),
            new Post(4, "tourist", "UPD: The scoring distribution is 500-1000-1500-2000-2250-2750.", 11)
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
