package com.music_server.mvp.mappers;

public interface Mapper <A, B>{

    B mapTo(A a);

    A mapFrom(B b);
}
