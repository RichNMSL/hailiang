package com.action;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.internal.utils.ServiceUtils;
import com.obs.services.model.CopyObjectResult;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class obstest {
    public static void main(String[] args) throws IOException {
        String endPoint = "https://obs.cn-east-3.myhuaweicloud.com";
        String ak = "XDV1IIQ7FZVRFOAKZTE2";
        String sk = "GHKQYWRULHAf1oSxLNTdU6en0x4BCHDHvdNpsYwY";

        String bucketName = "obs-datalake-dev";

        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);

        ObsClient obsClient = new ObsClient(ak, sk, config);

        try{
            ListObjectsRequest request = new ListObjectsRequest(bucketName);
            request.setMaxKeys(1000);
            // 设置文件夹分隔符"/"
            request.setDelimiter("/");
            ObjectListing result = obsClient.listObjects(request);

           List<String> list=listObjectsByPrefix(obsClient, request, result);
            System.out.println(list);

//            obsClient.copyObject("obs-datalake", obsObject.getObjectKey(),
//                    "obs-datalake-dev", obsObject.getObjectKey());
        }
        catch (ObsException e)
        {
            // 复制失败
            System.out.println("HTTP Code: " + e.getResponseCode());
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());

            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
        }

    }

    static List<String> listObjectsByPrefix(ObsClient obsClient, ListObjectsRequest request, ObjectListing result) throws ObsException
    {

        List<String> list=new ArrayList<String>();
        for(String prefix : result.getCommonPrefixes()){
            if(prefix.equals("cfg.db/")){
                request.setPrefix(prefix);
                result  = obsClient.listObjects(request);
                for(ObsObject obsObject : result.getObjects()){
                        list.add(obsObject.getObjectKey());

                }
                listObjectsByPrefix(obsClient, request, result);
            }else{
                break;
            }

        }
        return list;
    }
}
