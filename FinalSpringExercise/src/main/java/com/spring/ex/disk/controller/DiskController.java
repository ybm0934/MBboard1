package com.spring.ex.disk.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.ex.common.Utility;
import com.spring.ex.disk.model.DiskVO;

@Controller
public class DiskController {

	private static final String MYID = "admin";
	private String fixPath = Utility.PATH + MYID + "/";

	@RequestMapping("/disk.do")
	public String diskList(Model model) {
		File file = new File(fixPath);
		File[] files = file.listFiles();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		List<DiskVO> list = new ArrayList<DiskVO>();
		for(int i = 0; i < files.length;i++) {
			String name = files[i].getName();
			String type = (files[i].isDirectory()) ? "folder" : "file";
			Double size = (double) Math.round((files[i].length() / 1024.0 / 1024.0 * 100) / 100.0);
			Date mod = new Date(files[i].lastModified());
			String lastMod = sdf.format(mod);
			
			DiskVO diskVo = new DiskVO();
			
			diskVo.setName(name);
			diskVo.setType(type);
			diskVo.setSize(size);
			diskVo.setLastMod(lastMod);
			
			list.add(diskVo);
		}
		
		model.addAttribute("list", list);

		return "disk/disk";
	}

	@RequestMapping("/makeFolder.do")
	public void makeFolder(String path) {
		File folder = new File(fixPath + path);

		if (!folder.exists()) {
			folder.mkdir();
			System.out.println("폴더를 생성하였습니다.");
			System.out.println("경로 : " + fixPath + path);
		} else {
			System.out.println("이미 폴더가 존재합니다.");
		}
	}

}
