package kr.tpsw.rsquest.convert;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import kr.tpsw.rsquest.WordPressParsing;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TimeCheck {

	private static boolean hasRsTimer = false;

	public static void shutDownTimeCheck() {
		if (hasRsTimer) {
			TimeCheck2.shutDownRegister();
		}
	}

	public static boolean initTimeCheck(Plugin pl) {
		hasRsTimer = false;
		Plugin rstimer = Bukkit.getPluginManager().getPlugin("RsTimer");
		if (rstimer == null) {
			File file = new File("plugins\\RsTimer.jar");
			if (!file.exists()) {
				try {
					System.out.println("[RsQuest] RsTimer 플러그인을 찾을수가 없습니다! 다운로드 중...");
					downLoadRsTimer(file);
					System.out.println("[RsQuest] RsTimer 플러그인을 다운로드 완료");
				} catch (Exception e) {
					System.out.println("[RsQuest] RsTimer 플러그인 다운로드 도중 오류 발생! 오류 로그는 아래 첨부합니다.");
					e.printStackTrace();
					return false;
				}
			} else {
				System.out.println("[RsQuest] 이미 RsTimer.jar파일이 존재하지만 정상적으로 찾을수가 없습니다.");
				System.out.println("[RsQuest] 랜덤 퀘스트 자동 업데이트를 사용하고 싶으시면, 해당 파일을 제거후에 서버를 다시 열어주세요.");
				return false;
			}// 파일 다운로드

			try {
				Bukkit.getPluginManager().loadPlugin(file);
				System.out.println("[RsQuest] RsTimer 플러그인을 서버에 불러오는데 성공했습니다.");
			} catch (Exception e) {
				System.out.println("[RsQuest] RsTimer 플러그인 로드 도중 오류 발생! 오류 로그는 아래에 첨부합니다.");
				e.printStackTrace();
				return false;
			}

			rstimer = Bukkit.getPluginManager().getPlugin("RsTimer");
			if (rstimer == null) {
				System.out.println("[RsQuest] RsTimer 플러그인을 서버에서 찾을수가 없습니다! 잘못된 파일이 다운로드 되었습니다.");
				return false;
			} else {
				System.out.println("[RsQuest] RsTimer 플러그인을 서버에서 찾았습니다.");
			}

			Bukkit.getPluginManager().enablePlugin(rstimer);
			if (rstimer.isEnabled()) {
				System.out.println("[RsQuest] RsTimer 플러그인이 활성화 된 것을 확인했습니다.");
			} else {
				System.out.println("[RsQuest] RsTimer 플러그인이 활성화 되지 않았습니다.");
				return false;
			}
		}// 플러그인 다운로드 후 로드
		
		String needv = "1.0.4";
		double needd = WordPressParsing.trasferVersion(needv);

		String targetv = rstimer.getDescription().getVersion();
		double targetd = WordPressParsing.trasferVersion(targetv);
		if (targetd < needd) {// 타겟 버전이 필요 버전보다 낮을 경우
			System.out.println("[RsQuest] RsTimer 플러그인의 버전이 연동에 필요한 버전보다 낮습니다. 필요 버전:" + needv);
			System.out.println("[RsQuest] RsTimer 플러그인 업데이트를 위해 콘솔에 [timeupdate true]명령어를 입력해주세요.");
			return false;
		}

		try {
			TimeCheck2.initRegister(rstimer);
			System.out.println("[RsQuest] RsTimer 플러그인과 연동 성공!");
		} catch (Exception e) {
			System.out.println("[RsQuest] RsTimer 플러그인과 연동 도중 오류 발생! 오류 로그는 아래에 첨부합니다.");
			e.printStackTrace();
		}
		hasRsTimer = true;
		// rstimer이 없을 경우 오류나니까 클래스 변경
		return true;
	}

	private static void downLoadRsTimer(File file) throws Exception {
		String PLUGIN_NAME = "RsTimer";
		String PLUGIN_NAME_LOWER = PLUGIN_NAME.toLowerCase();
		String PLUGIN_UPDATE_URL = null;

		String postid = "202";
		String posturl = "http://tpsw.or.kr/" + postid;
		StringBuilder sb = new StringBuilder();

		int i1 = 0;
		int i2 = 0;
		StringBuilder sb2;

		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(posturl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line).append('\n');
			}
		} catch (Exception e) {
			throw e;
		}

		i1 = builder.indexOf("<div class=\"post-content\">");
		builder.delete(0, i1);
		i1 = builder.indexOf("<p>");
		builder.delete(0, i1 + 3);
		i1 = builder.indexOf("</div>");
		String[] args = builder.substring(0, i1).replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("<p>", "").replace("\n", "").trim().split("</p>");
		for (String line : args) {
			sb.append(line).append('\n');
		}

		{
			i1 = sb.indexOf("<" + PLUGIN_NAME_LOWER + ">");
			i2 = sb.indexOf("</" + PLUGIN_NAME_LOWER + ">");
			sb2 = new StringBuilder(sb.substring(i1 + PLUGIN_NAME_LOWER.length() + 3, i2 - 1));
		}// 자르기

		{
			i1 = sb2.indexOf("<download>");
			i2 = sb2.indexOf("</download>");
			if (i1 == -1 && i2 == -1) {
				// 넘어간다
				PLUGIN_UPDATE_URL = null;
			} else {
				PLUGIN_UPDATE_URL = sb2.substring(i1 + 8 + 3, i2 - 1);
			}
		}// 업데이트 주소
		wordPressDownload(PLUGIN_UPDATE_URL, new File(file.getPath()));
	}

	private static void wordPressDownload(String surl, File file) {
		try {
			URL url = new URL(surl);
			URLConnection urlc = url.openConnection();
			InputStream is = urlc.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			int r;
			byte[] buffer = new byte[1024];
			while ((r = bis.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, r);
			}
			bos.close();
			bis.close();
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
