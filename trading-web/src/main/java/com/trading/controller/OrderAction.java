package com.trading.controller;

import com.lip.trading.matchbox.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Lip on 2017-01-10 11:31
 */
@Controller
public class OrderAction {
    public @ResponseBody
    Map<String,Object> order(Order order)
    {
        return null;
    }
}
