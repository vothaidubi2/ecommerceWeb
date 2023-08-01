package com.asm.utils;

import java.io.IOException;
import java.io.ObjectInputFilter.Config;

import org.apache.http.client.ClientProtocolException;
import org.bson.Document;

public class Momo {
   public static Document create(Double amount, String extraData) throws ClientProtocolException, IOException{
        Document body = new Document();

        body.put("amount", amount);
        body.put("extraData", extraData);

        return HttpClient.post("https://server.duan1.lavenes.com/create_pay", body, new Document());
   }
}
