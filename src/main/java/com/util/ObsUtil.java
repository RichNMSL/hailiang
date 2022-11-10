package com.util;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;

public class ObsUtil {
    String endPoint = "https://obs.cn-east-3.myhuaweicloud.com";
    String ak = "GHKQYWRULHAf1oSxLNTdU6en0x4BCHDHvdNpsYwY";
    String sk = "GHKQYWRULHAf1oSxLNTdU6en0x4BCHDHvdNpsYwY";
    String bucketName = " obs-datalake-dev";
    ObsConfiguration config = new ObsConfiguration();


    ObsClient obsClient = new ObsClient(ak, sk, endPoint);


// 使用访问OBS


}
