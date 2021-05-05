package fr.theobosse.scoreboardapi.api;

import java.util.ArrayList;
import java.util.List;

public class AnimatedText {

    private final List<String> anim;
    private final int frames;

    private int pointer;
    private int frame;

    public AnimatedText(List<String> anim, int frames) {
        this.frames = frames;
        this.anim = anim;
        this.pointer = 0;
        this.frame = 0;
    }

    public String next() {
        if (frame >= frames) {
            if (pointer < anim.size() - 1)
                pointer += 1;
            else pointer = 0;
            frame = 0;
        } else frame += 1;

        return anim.get(pointer);
    }

}
