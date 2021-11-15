package com.spring.ex.disk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.ex.common.Utility;
import com.spring.ex.disk.model.DiskVO;

@Controller
public class DiskController {

	private static final String MYID = "admin";
	private String fixPath = Utility.PATH + MYID + "/";
	private StringBuffer sb;

	@RequestMapping("/disk.do")
	public String diskList(@RequestParam(value = "inPath", defaultValue = "") String inPath, Model model) {
		if (inPath.equals(""))
			inPath = fixPath;

		File file = new File(inPath);
		File[] orgFiles = file.listFiles();

		// 폴더, 파일 정렬 List
		ArrayList<File> folders = new ArrayList<File>();
		ArrayList<File> files = new ArrayList<File>();

		for (int i = 0; i < orgFiles.length; i++) {
			if (orgFiles[i].isDirectory()) {
				folders.add(orgFiles[i]);
			}
		}

		for (int i = 0; i < orgFiles.length; i++) {
			if (orgFiles[i].isFile()) {
				files.add(orgFiles[i]);
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		List<DiskVO> list = new ArrayList<DiskVO>();
		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < folders.size(); i++) {
			String name = folders.get(i).getName();
			String type = "folder";
			Date mod = new Date(folders.get(i).lastModified());
			String lastMod = sdf.format(mod);
			String path = folders.get(i).getPath() + "/";
			path = path.replace("\\", "/");
			String upPath = folders.get(i).getParentFile().getPath() + "/";
			upPath = upPath.replace("\\", "/");
			String upName = folders.get(i).getParentFile().getName();

			/*
			 * String parentPath = inPath.substring(fixPath.length()); String[] pathArr =
			 * parentPath.split("/"); for (int j = pathArr.length - 1; j > 0 ; j--) {
			 * parentPath = parentPath.substring(0, parentPath.lastIndexOf("/")); String
			 * prName = pathArr[j]; String prPath = fixPath + parentPath;
			 * 
			 * System.out.println(prName); System.out.println(prPath);
			 * 
			 * map.put(prName, prPath); }
			 */

			String parentPath = inPath.substring(fixPath.length());
			String[] pathArr = parentPath.split("/");
			for (int j = pathArr.length - 1; j > 0; j--) {
				parentPath = parentPath.substring(0, parentPath.lastIndexOf("/"));
				String prName = pathArr[j];
				String prPath = fixPath + parentPath;

				System.out.println(prName);
				System.out.println(prPath);

				map.put(prName, prPath);
			}

			DiskVO diskVo = new DiskVO();
			diskVo.setName(name);
			diskVo.setType(type);
			diskVo.setLastMod(lastMod);
			diskVo.setPath(path);
			diskVo.setUpPath(upPath);
			diskVo.setUpName(upName);

			list.add(diskVo);
		}

		for (int i = 0; i < files.size(); i++) {
			String name = files.get(i).getName();
			String type = "file";
			Double size = (double) (Math.round((files.get(i).length() / 1024.0 / 1024.0 * 100)) / 100.0);
			String sizeType = "MB";
			if (size < 0.1) {
				size = (double) (Math.round((files.get(i).length() / 1024.0 * 100)) / 100.0);
				sizeType = "KB";
			}
			Date mod = new Date(files.get(i).lastModified());
			String lastMod = sdf.format(mod);

			DiskVO diskVo = new DiskVO();
			diskVo.setName(name);
			diskVo.setType(type);
			diskVo.setSize(size);
			diskVo.setSizeType(sizeType);
			diskVo.setLastMod(lastMod);

			list.add(diskVo);
		}

		sb = new StringBuffer();
		String parentTag = parentPath(file);

		model.addAttribute("list", list);
		model.addAttribute("inPath", inPath);
		model.addAttribute("parentTag", parentTag);

		return "disk/disk";
	}

	// 부모 경로 구하기
	public String parentPath(File folder) {
		String path = folder.getPath();
		String pathName = folder.getName();
		String prName = folder.getParentFile().getName();
		String prPath = folder.getParentFile().getPath();
		path = path.replace("\\", "/") + "/";
		prPath = prPath.replace("\\", "/") + "/";
		System.out.println("path : " + path);
		System.out.println("fixPath : " + fixPath);
		System.out.println("prPath : " + prPath);
		if ((fixPath.length() < path.length())) {
			sb.append(" &gt; <a href=\"#\" onclick=\"upFolder('" + path + "')\">" + pathName + "</a>");

			File file = new File(prPath);
			parentPath(file);
		}
		String parentTag = sb.toString();

		return parentTag;
	}

	@RequestMapping("/makeFolder.do")
	public String makeFolder(@RequestParam(value = "path", defaultValue = "") String path) {
		System.out.println("폴더 만들기 실행");
		System.out.println("path : " + path);

		if (path.equals(""))
			path = fixPath;
		// path = path.replace("\\", "\\\\");
		String folderName = "새 폴더";
		File folder = new File(path + folderName);

		if (!folder.exists()) {
			folder.mkdir();
			System.out.println("폴더를 생성하였습니다.");
		} else {
			int cnt = 0;
			while (true) {
				folderName = "새 폴더 (" + ++cnt + ")";
				folder = new File(path + folderName);
				if (!folder.exists()) {
					folder.mkdir();
					break;
				}
			}
			System.out.println("이미 폴더명이 존재하여 새 폴더명을 정의합니다.");
		}

		return "redirect:/disk.do";
	}

	@RequestMapping("/fileUpload.do")
	public String fileUpload(@RequestParam("path") String path, @RequestParam("files") MultipartFile[] files)
			throws IOException {
		System.out.println("파일 업로드 실행");
		System.out.println("path : " + path);
		System.out.println("files.length " + files.length);

		if (path.equals(""))
			path = fixPath;
		// path = path.replace("\\", "\\\\");

		if (files != null) {
			UUID uuid = UUID.randomUUID();
			FileOutputStream fos = null;
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getOriginalFilename();
				File file = new File(path + fileName);
				if (file.exists()) {
					fileName = uuid.toString() + "_" + files[i].getOriginalFilename();
					file = new File(path + fileName);
				}
				byte[] bytes = files[i].getBytes();
				fos = new FileOutputStream(file);
				fos.write(bytes);
			}

			if (fos != null)
				fos.close();
		}

		return "redirect:/disk.do";
	}

	@RequestMapping("/deleteFile.do")
	public String deleteFile(@RequestParam("path") String path, @RequestParam("checked") String checked) {
		System.out.println("파일 삭제 실행");
		System.out.println("path : " + path);
		System.out.println("checked : " + checked);

		if (path.equals(""))
			path = fixPath;
		// path = path.replace("\\", "\\\\");
		String[] files = checked.split(":");
		for (int i = 0; i < files.length; i++) {
			File file = new File(path + files[i]);
			if (file.exists()) {
				System.out.println(file.getName() + " 파일을 삭제하였습니다.");
				file.delete();
			} else {
				System.out.println(file.getName() + " 파일이 존재하지 않습니다.");
			}
		}

		return "redirect:/disk.do";
	}

	@RequestMapping("/fileDownload.do")
	public String fileDownload(@RequestParam(value = "path", defaultValue = "") String path,
			@RequestParam("fileName") String fileName, Model model) {
		System.out.println("파일 다운로드 요청");
		System.out.println("path : " + path);
		System.out.println("fileName : " + fileName);

		if (path.equals(""))
			path = fixPath;
		// path = path.replace("\\", "\\\\");

		File file = new File(path + fileName);
		String url = "";
		if (file.exists()) {
			if (file.isDirectory()) {

			}

			model.addAttribute("downloadFile", file);

			url = "downloadView";
		} else {
			System.out.println("파일이 존재하지 않습니다.");
			url = "disk/message";
		}

		return url;
	}

}
