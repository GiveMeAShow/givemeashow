package giveme.inserters;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Update database from the AMAZON S3 service
 * 
 * @author Axel
 * 
 */
@Component
@Repository
public class AwsFilesAutoInserter
{
	private static final Logger	LOGGER	= Logger.getLogger(AwsFilesAutoInserter.class.getName());

	AmazonS3Client				s3;
	private static String		cloudUrl;
	@Autowired
	Inserter					inserter;

	public AwsFilesAutoInserter()
	{
		Properties props = new Properties();
		try
		{
			props.load(AwsFilesAutoInserter.class.getClassLoader().getResourceAsStream("givemeashow.properties"));
			String AWKEY = props.getProperty("accessKey");
			String AWSECRET = props.getProperty("accessSecret");
			cloudUrl = props.getProperty("cloudUrl");
			s3 = new AmazonS3Client(new BasicAWSCredentials(AWKEY, AWSECRET));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Show	show	= new Show();
	private Season	season	= new Season();
	private ISOLang	isoLang	= new ISOLang();

	private void visitAll(List<S3ObjectSummary> objectSummaries)
	{
		for (S3ObjectSummary awsObjSum : objectSummaries)
		{
			String key = awsObjSum.getKey();
			String name = key;
			String[] paths = StringUtils.split(key, "/");

			int level = paths.length;
			switch (level)
			{
			case 1:
				// LOGGER.info(paths[0]);
				show = inserter.insertShow(paths[0]);
				break;
			case 2:
				season = inserter.insertSeason(show, paths[1]);
				// LOGGER.info(paths[1] + " is a season");
				break;
			case 3:

				isoLang = inserter.insertLang(paths[2]);
				// LOGGER.info(paths[2] + " is a lang");
				break;
			case 4:
				Video video = new Video();
				createUrlAndRelativePath(key, video);
				video = inserter.insertVideo(show, season, paths[3], isoLang, key, video);
				// LOGGER.info(paths[3] + " is a video");
				break;
			default:
				break;
			}
		}
	}

	private void createUrlAndRelativePath(String relativePath, Video video)
	{
		video.setUrl(cloudUrl + "/" + relativePath);
		video.setRelativePath(relativePath);
		LOGGER.info("video relative path : " + relativePath);
	}

	public void visitAll()
	{
		ObjectListing listing = s3.listObjects("givemeashowvideos");
		visitAll(listing.getObjectSummaries());
	}
}
