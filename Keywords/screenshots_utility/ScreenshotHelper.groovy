package screenshots_utility

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy


public class ScreenshotHelper {

	/*
	 * compare actual & expected images
	 * 
	 * object - TestObject for which screenshot needs to capture
	 * actualImageFilename - file name for captured screenshot
	 * expectedImageFilepath - file path of expected image
	 * 
	 */
	@Keyword
	def compareImages(TestObject object, File expectedImageFilepath, String actualImageFilename){

		//read expected image from specified path
		BufferedImage expectedImage = ImageIO.read(expectedImageFilepath)

		//store actual image in specified path
		String elementScreenshot = WebUI.takeElementScreenshot(RunConfiguration.getReportFolder() +'\\' +actualImageFilename+'.png' ,object)

		String elementScreenshotPath = RunConfiguration.getReportFolder() +'\\' + elementScreenshot

		println(elementScreenshotPath)

		//read actual image from specified path
		BufferedImage actualImage = ImageIO.read(new File(elementScreenshotPath))

		PointsMarkupPolicy diffMarkupPolicy = new PointsMarkupPolicy();

		if(actualImageFilename.contains("SIP")){
			diffMarkupPolicy.setDiffSizeTrigger(210) //35% ignored
		}
		else {
			diffMarkupPolicy.setDiffSizeTrigger(160) //20%ignored
		}


		ImageDiffer imgDiff = new ImageDiffer()

		imgDiff.withDiffMarkupPolicy(diffMarkupPolicy)

		ImageDiff diff = imgDiff.makeDiff(expectedImage, actualImage)



		if(diff.hasDiff()) {

			println(diffMarkupPolicy.getDiffSize())

			return false
		}
		else {

			println(diffMarkupPolicy.getDiffSize())

			return true
		}
	}
}