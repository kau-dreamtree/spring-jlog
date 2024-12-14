package com.jlog.domain.log;

import java.io.Serializable;

public interface LogDto extends Serializable {

    Long id();

    String roomCode();

    String username();

    Long expense();

    String memo();
}
