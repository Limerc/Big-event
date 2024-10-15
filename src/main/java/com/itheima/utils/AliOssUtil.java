package com.itheima.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.InputStream;

public class AliOssUtil {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";

    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    // 直接定义两个变量存储AccessKey和AccessKeySecret。
    private static final String ACCESSKEYID = "Your ACCESSKEYID";
    private static final String ACCESSKEYSECRET = "Your ACCESSKEYSECRET";

    // 填写Bucket名称，例如examplebucket。
    private static final String BUCKETNAME = "web-framework-hk1";

    // 将objectName和本地文件路径in以参数形式传入，上传文件到OSS。
    // 将文件的访问路径作为参数返回
    public static String uploadFile(String objectName, InputStream in) throws Exception {
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt
        // 存储对象的名字（可以是字符、视频、图片等）

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID , ACCESSKEYSECRET);
        String url = "";
        try {
            // 填写字符串。这个对象将来会被传递给OSS服务器
            String content = "Hello OSS，你好世界";

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETNAME, objectName,in);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 根据url的组成方式，拼接出完整的url=htts://BUCKETNAME.ENDPOINT/objectName
            // ENDPOINT是从最后一个/往后进行截取，所以要加1
            url = "https://" + BUCKETNAME + "." + ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1) + "/" + objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
