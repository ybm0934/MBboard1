package com.spring.ex.disk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zeroturnaround.zip.ZipUtil;

import com.spring.ex.common.Utility;
import com.spring.ex.disk.model.DiskVO;

@Controller
public class DiskController {

	private static final String MYID = "admin";
	private String fixPath = Utility.PATH + MYID + "/";
	private AES256 aes256;
	private StringBuffer sb;

	public DiskController() {
		aes256 = new AES256();
	}

	@RequestMapping("/disk.do")
	public String diskList(@RequestParam(value = "inPath", defaultValue = "") String inPath, Model model)
			throws Exception {
		inPath = aes256.decrypt(inPath); // 경로 복호화

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
		int no = 0; // 파일 번호

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

			DiskVO diskVo = new DiskVO();
			diskVo.setNo(no++);
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
			Double size = (double) (Math.round((files.get(i).length() / 1024.0 / 1024.0 / 1024.0 * 100)) / 100.0);
			String sizeType = "GB";
			if (size < 0.1) {
				size = (double) (Math.round((files.get(i).length() / 1024.0 / 1024.0 * 100)) / 100.0);
				sizeType = "MB";
			} else if (size < 0.01) {
				size = (double) (Math.round((files.get(i).length() / 1024.0 * 100)) / 100.0);
				sizeType = "KB";
			}
			Date mod = new Date(files.get(i).lastModified());
			String lastMod = sdf.format(mod);
			String path = files.get(i).getPath() + "/";
			path = path.replace("\\", "/");
			String extension = path.substring(path.lastIndexOf("."), path.length() - 1);
			extension = extension.toLowerCase();
			String img = ".ai .bmp .gif .jpg .jpeg .jpe .jfif .jp2 .j2c .pcx .psd .png .tga .taga .tif .tiff .ico";
			String audio = ".mp3 .flac .aac .wav .aiff .ogg .wma .au .mid";
			String video = ".webm .mkv .flv .avi .mts .m2ts .ts .mov .wmv .rm .rmvb .asf .amv .mp4 .m4p .m4v .mpg .mp2 .mpeg .mpe .mpv .svi .3gp .f4v .f4p .f4a .f4b";
			String doc = ".doc .docm .docx .rtf .txt .hwp";
			if (img.contains(extension)) {
				type = "img";
			} else if (audio.contains(extension)) {
				type = "audio";
			} else if (video.contains(extension)) {
				type = "video";
			} else if (doc.contains(extension)) {
				type = "doc";
			} else if (extension.equals(".pdf")) {
				type = "pdf";
			} else if (extension.equals(".ppt") || extension.equals(".pptx")) {
				type = "ppt";
			} else if (extension.equals(".xls")) {
				type = "xls";
			} else if (extension.equals(".zip")) {
				type = "zip";
			} else {
				type = "file";
			}

			DiskVO diskVo = new DiskVO();
			diskVo.setNo(no++);
			diskVo.setName(name);
			diskVo.setType(type);
			diskVo.setSize(size);
			diskVo.setSizeType(sizeType);
			diskVo.setLastMod(lastMod);
			diskVo.setPath(path);
			diskVo.setExtension(extension);

			list.add(diskVo);
		}

		sb = new StringBuffer();
		String parentTag = parentPath(file);

		inPath = inPath.replace("\\", "/");
		inPath = aes256.encrypt(inPath); // 경로 암호화

		model.addAttribute("list", list);
		model.addAttribute("inPath", inPath);
		model.addAttribute("parentTag", parentTag);

		return "disk/disk";
	}

	// 부모 경로 구하기
	public String parentPath(File folder) throws Exception {
		String path = folder.getPath();
		System.out.println("path : " + path);
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		String pathName = folder.getName();
		System.out.println("pathName : " + pathName);
		String prPath = folder.getParentFile().getPath();
		System.out.println("prPath : " + prPath);
		path = path.replace("\\", "/") + "/";
		path = aes256.encrypt(path);
		prPath = prPath.replace("\\", "/") + "/";

		if ((fixPath.length() < path.length())) {
			sb.insert(0, " &gt; <a href=\"#\" onclick=\"upFolder('" + path + "')\">" + pathName + "</a>");

			File file = new File(prPath);
			parentPath(file);
		}

		return sb.toString();
	}

	@RequestMapping("/makeFolder.do")
	public String makeFolder(@RequestParam(value = "path", defaultValue = "") String path, RedirectAttributes redirect)
			throws Exception {
		path = aes256.decrypt(path);

		if (path.equals(""))
			path = fixPath;

		String folderName = "새 폴더";
		File folder = new File(path + folderName);
		boolean bool = false;
		if (!folder.exists()) {
			Thread.sleep(1000); // 1초 지연
			bool = folder.mkdir();
		} else {
			int cnt = 0;
			while (true) {
				folderName = "새 폴더 (" + ++cnt + ")";
				folder = new File(path + folderName);
				if (!folder.exists()) {
					bool = folder.mkdir();
					break;
				}
			}
			if (bool == true)
				System.out.println("이미 폴더명이 존재하여 새 폴더명을 정의합니다.");
		}

		if (bool == true) {
			System.out.println("폴더를 생성하였습니다.");
		} else {
			System.out.println("폴더 생성에 실패하였습니다.");
		}

		path = aes256.encrypt(path);
		redirect.addAttribute("inPath", path);

		return "redirect:/disk.do";
	}

	@RequestMapping("/fileUpload.do")
	public String fileUpload(@RequestParam("path") String path, @RequestParam("files") MultipartFile[] files,
			RedirectAttributes redirect) throws Exception {
		System.out.println("파일 업로드 실행");
		System.out.println("files.length " + files.length);

		path = aes256.decrypt(path);

		if (path.equals(""))
			path = fixPath;

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

		path = aes256.encrypt(path);
		redirect.addAttribute("inPath", path);

		return "redirect:/disk.do";
	}

	@RequestMapping("/folderUpload.do")
	public String folderUpload(File[] files) {
		System.out.println("folderUpload 실행");
		System.out.println("length : " + files.length);
		for (int i = 0; i < files.length; i++) {
			/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
		}

		return "redirect:/disk.do";
	}

	@RequestMapping("/deleteFile.do")
	public String getDelete(@RequestParam(value = "checked", defaultValue = "") String checked,
			RedirectAttributes redirect, Model model) throws Exception {
		// deleteFile 재귀 함수로 인한 복호화 불가능으로 사전 복호화 후 다시 바인딩 작업
		String[] checkList = checked.split(">"); // join 해제
		for (int i = 0; i < checkList.length; i++) {
			checkList[i] = aes256.decrypt(checkList[i]); // 파라미터 복호화
		}
		checked = String.join(">", checkList); // 다시 바인딩

		Map<Boolean, String> map = deleteFile(checked);

		String defaultUrl = "redirect:/disk.do";
		for (boolean bool : map.keySet()) {
			String url = map.get(bool);
			if (bool == true) {
				url = aes256.encrypt(url);
				redirect.addAttribute("inPath", url);
			} else {
				model.addAttribute("msg", "파일이 존재하지 않습니다.");
				defaultUrl = url;
			}
		}

		return defaultUrl;
	}

	// 파일 세부 삭제
	public Map<Boolean, String> deleteFile(String checked) throws Exception {
		Map<Boolean, String> map = new HashMap<Boolean, String>();
		String[] checkList = checked.split(">");
		File file = null;
		boolean bool = false;
		String url = "";
		if (checkList.length > 0) {
			for (int j = 0; j < checkList.length; j++) {
				file = new File(checkList[j]);
				if (file.exists()) {
					if (file.isFile()) {
						file.delete();
					} else {
						File[] files = file.listFiles();
						if (files.length != 0) {
							for (int i = 0; i < files.length; i++) {
								if (files[i].isDirectory()) {
									deleteFile(files[i].getPath());
								}

								files[i].delete();
							} // for
						} // if

						file.delete();
					} // if

					bool = true;
					url = file.getParentFile().getPath() + "/";
					url = url.replace("\\", "/");
				} else {
					System.out.println("파일이 존재하지 않습니다.");

					bool = false;
					url = "disk/message";
					break;
				} // if
			} // for
		} // if

		map.put(bool, url);

		return map;
	}

	@RequestMapping("/fileDownload.do")
	public String download(@RequestParam(value = "path", defaultValue = "") String path, Model model) throws Exception {
		path = aes256.decrypt(path);
		File file = new File(path);
		String url = "";
		if (file.isFile()) {
			url = fileDownload(path, model);
		} else {
			url = folderDownload(path, model);
		}

		return url;
	}

	// 파일 다운로드
	public String fileDownload(String path, Model model) {
		System.out.println("파일 다운로드 요청");
		System.out.println("path : " + path);

		if (path.equals(""))
			path = fixPath;

		File file = new File(path);
		String url = "";
		if (file.exists()) {
			model.addAttribute("downloadFile", file);
			url = "downloadView";
		} else {
			System.out.println("파일이 존재하지 않습니다.");
			url = "disk/message";
		}

		return url;
	}

	// 폴더 다운로드 (압축)
	public String folderDownload(String path, Model model) {
		if (path.equals(""))
			path = fixPath;

		String tempPath = Utility.PATH + MYID + "_temp/"; // 압축 파일 임시 저장 경로
		File file = new File(tempPath);
		File[] files = file.listFiles();
		if (files.length > 0) {
			System.out.println("tempPath 폴더 초기화를 실시합니다.");
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}

		file = new File(path);
		files = file.listFiles();
		long date = System.currentTimeMillis(); // 파일명 중복 방지용 시간
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String zipName = file.getName() + "_" + sdf.format(date) + ".zip"; // 압축 파일 이름(랜덤) 및 확장자 지정
		ZipOutputStream zos = null;
		String url = "";
		if (files.length > 0) {
			try {
				boolean flag = false;
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory())
						flag = true;
				}

				if (flag == true) {
					ZipUtil.pack(new File(path), new File(tempPath + zipName));
				} else {
					// 파일 압축 시작
					zos = new ZipOutputStream(new FileOutputStream(tempPath + zipName)); // 압축 파일 임시 저장 경로
					FileInputStream fis = null;
					byte[] buffer = new byte[1024];
					for (int i = 0; i < files.length; i++) {

						zos.putNextEntry(new ZipEntry(files[i].getName())); // 압축 파일에 저장될 파일명

						fis = new FileInputStream(files[i]); // 압축 대상 파일
						int len;
						while ((len = fis.read(buffer)) > 0) // 파일을 1024 바이트로 읽어옴
							zos.write(buffer, 0, len); // 읽은 파일을 ZipOutputStream에 write

						zos.closeEntry();
						fis.close();

					}

					zos.close(); // 파일 압축 종료
				}

				// 파일 다운로드 시작
				file = new File(tempPath + zipName);
				if (file.exists()) {
					model.addAttribute("downloadFile", file);
					url = "downloadView";
				} else {
					System.out.println("파일이 존재하지 않습니다.");
					url = "disk/message";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (zos != null)
					zos = null;
			}
		} // if

		return url;
	}

}
