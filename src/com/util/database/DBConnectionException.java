package com.util.database;

import com.blogzhou.common.exception.CustomException;

public class DBConnectionException extends CustomException
{

    public DBConnectionException()
    {
    }

    public DBConnectionException(String errmsg)
    {
        super(errmsg);
    }
}