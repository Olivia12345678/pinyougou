package com.pinyougou.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import entity.Result;
import util.FastDFSClient;

@RestController
public class UploadController {
	
	@Value("${FILE_SERVER_URL}")
	private String file_server_url;

	@RequestMapping("/upload")
	public Result upload(MultipartFile file){
		
		String originalFilename = file.getOriginalFilename();//��ȡ�ļ���
		String extName=originalFilename.substring( originalFilename.lastIndexOf(".")+1);//�õ���չ��
		
		try {
			util.FastDFSClient client=new FastDFSClient("classpath:config/fdfs_client.conf");
			String fileId = client.uploadFile(file.getBytes(), extName);
			String url=file_server_url+fileId;//ͼƬ������ַ
			return new Result(true, url);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "�ϴ�ʧ��");
		}
		
	}
	
	
}
