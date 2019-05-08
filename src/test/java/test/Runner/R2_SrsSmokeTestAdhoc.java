package test.Runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.Framework.WEBHelper;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import test.Utilities.Mail;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber",
		"json:target/JSON/R2_SrsSmokeTestAdhocReport.json" }, features = { "@target/SrsAdhocRerun.txt" }, glue = {
				"cucumber.Framework", "webApp.Compass", "webApp.Seoreseller", "webApp.PayPerContent",
				"webApp.Mailbox" }, plugin = {
						"com.cucumber.listener.ExtentCucumberFormatter:target/R2_SrsSmokeTestAdhocReport.html",
						"rerun:target/SrsAdhocRerun2.txt" }

)

public class R2_SrsSmokeTestAdhoc extends WEBHelper {
	@BeforeClass
	public static void SecondBeforeClass() throws Exception {
		log.info("Execution is started from First Runner Test - BeforeClass Annotation");
	}

	@AfterClass
	public static void SecondAfterClass() throws Exception {
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("target/SrsAdhocRerun.txt"));

		String htmlFilePath = System.getProperty("user.dir") + "\\target\\R2_SrsSmokeTestAdhocReport.html";
		String htmlFileContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));

		if (htmlFileContent.contains("'status fail'")) {

			Mail.SendFailedReport("R1_SrsSmokeTestAdhocReport.html", GetApplication() + GetTestEnv(),
					"[SMOKE TEST]: " + GetApplication() + GetTestEnv() + " (Adhoc:FAILED) - ");
			log.info("Execution is ended from Second Runner - Test AfterClass Annotation");
		}

		if (br.readLine() == null) {
			System.out.println("File is EMPTY");

		} else {
			System.out.println("File is not EMPTY");
		}
	}
}
