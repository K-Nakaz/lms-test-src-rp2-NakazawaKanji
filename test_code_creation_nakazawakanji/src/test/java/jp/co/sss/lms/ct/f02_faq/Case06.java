package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//画面遷移
		goTo("http://localhost:8080/lms");
		//ページ検証
		assertEquals("ログイン | LMS",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//データ入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA00");
		//入力エビデンス撮影
		getEvidence(new Object() {} ,"input");
		//ログインボタン押下
		webDriver.findElement(By.className("btn-primary")).click();
		//遷移先検証
		assertEquals("コース詳細 | LMS",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//「機能」プルダウンを展開
		webDriver.findElement(By.linkText("機能")).click();
		//リスト展開待ち
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("ヘルプ")));
		//「ヘルプ」リンクをクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();
		//遷移待ち
		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));
		//遷移先検証
		assertEquals("ヘルプ | LMS",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」リンクを押下
		webDriver.findElement(By.linkText("よくある質問")).click();
		//タブの切り替え処理のため、すべてのタブ番号を取得する
		List<String> tabList = new ArrayList<>(webDriver.getWindowHandles());
		//最新タブはリストの最後に位置するため、アクセス
		webDriver.switchTo().window(tabList.get(tabList.size()-1));
		//待ち処理
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
		//遷移先検証
		assertEquals("よくある質問 | LMS",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//画面遷移後の待ち処理
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("【研修関係】")));
		//カテゴリ検索欄の項目をクリック
		webDriver.findElement(By.linkText("【研修関係】")).click();
		//検索結果表示の待ち処理
		visibilityTimeout(By.className("mb10"),30);
		//検索結果リストの取得
		List<WebElement> searchResult = webDriver.findElements(By.className("mb10"));
		//検証
		assertEquals("Q.キャンセル料・途中退校について",searchResult.get(0).getText());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？",searchResult.get(1).getText());
		//証跡撮影用のスクロール
		scrollBy("300");
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//検索結果リストの取得
		List<WebElement> searchResult = webDriver.findElements(By.className("mb10"));
		//1番上の要素をクリック
		searchResult.get(0).click();
		//検証用テキスト
		String test = "A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。";
		assertEquals(test,webDriver.findElement(By.className("fs18")).getText());
		//証跡撮影
		getEvidence(new Object() {});
	}

}
