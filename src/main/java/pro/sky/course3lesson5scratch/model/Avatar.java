package pro.sky.course3lesson5scratch.model;

import org.springframework.beans.factory.annotation.Value;

public class Avatar {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
}
