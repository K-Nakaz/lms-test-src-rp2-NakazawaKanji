package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//10/2の詳細ボタンを押下
		webDriver.findElements(By.className("btn-default")).get(2).click();
		//待ち処理
		visibilityTimeout(By.cssSelector("input[type='submit'].btn.btn-default"), 10);
		//検証
		assertEquals("セクション詳細 | LMS",webDriver.getTitle());
		//レポートが未登録なことを確認するための処理
		List<WebElement> submitButton = webDriver.findElements(By.cssSelector("input[type='submit'].btn.btn-default"));
		assertEquals("提出済み週報【デモ】を確認する",submitButton.get(2).getAttribute("value"));
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//ボタンが見えないのでスクロール
		scrollBy("300");
		//ボタン取得と押下の処理
		webDriver.findElements(By.cssSelector("input[type='submit'].btn.btn-default")).get(2).click();
		//待ち処理
		visibilityTimeout(By.className("form-control"), 10);
		//検証
		assertEquals("レポート登録 | LMS",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//内容記入
		webDriver.findElement(By.cssSelector("textarea#content_1.form-control")).clear();
		webDriver.findElement(By.cssSelector("textarea#content_1.form-control")).sendKeys("週報のサンプルです。編集済です。");
		//証跡撮影
		getEvidence(new Object() {},"input");
		//ボタンが見えないのでスクロール
		scrollBy("400");
		//提出ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit'].btn.btn-primary")).click();
		//待ち処理
		visibilityTimeout(By.cssSelector("input[type='submit'].btn.btn-default"), 10);
		//検証
		assertEquals("セクション詳細 | LMS",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {},"regist");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		//「ようこそ受講生AA1さん」を押下
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();
		//待ち処理
		visibilityTimeout(By.cssSelector("h2"), 10);
		//検証
		assertEquals("ユーザー詳細",webDriver.getTitle());
		//証跡撮影
		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		//スクロール
		scrollBy("500");
		//週報のボタン取得と押下
		webDriver.findElements(By.cssSelector("input[type='submit'].btn.btn-default")).get(5).click();
		//待ち処理
		visibilityTimeout(By.cssSelector("h2"), 10);
		//検証
		assertEquals("週報【デモ】 2022年10月2日",webDriver.findElement(By.cssSelector("h2")).getText());
		//所感欄を探す（2番目のテーブルの2行目）
		List<WebElement> reportTableList = webDriver.findElements(By.tagName("table"));
		List<WebElement> reportTableRowList = reportTableList.get(2).findElements(By.tagName("td"));
		WebElement feeling = reportTableRowList.get(1);
		//内容確認
		assertEquals("週報のサンプルです。編集済です。",feeling.getText());
		//証跡撮影
		getEvidence(new Object() {});
	}

}
