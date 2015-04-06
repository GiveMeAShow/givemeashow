package giveme.inserters;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.shared.GiveMeProperties;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class AwsFilesAutoInserter
{
	private static final Logger	LOGGER	= Logger.getLogger(AwsFilesAutoInserter.class.getName());

	AmazonS3Client				s3;

	private static String		cloudUrl;

	@Autowired
	Inserter					inserter;

	@Autowired
	public GiveMeProperties		giveMeAShowProperties;


	public AwsFilesAutoInserter()
	{
		init();
	}

	public void init() {
		if (giveMeAShowProperties != null) {
			initS3();
		} else {
			LOGGER.error("Properties class not instanciated !");
		}
	}

	private void initS3() {
		cloudUrl = giveMeAShowProperties.getCloudUrl();
		String AWKEY = giveMeAShowProperties.getAWKEY();
		String AWSECRET = giveMeAShowProperties.getAWSECRET();
		s3 = new AmazonS3Client(new BasicAWSCredentials(AWKEY, AWSECRET));
	}

	public void setS3(AmazonS3Client s3) {
		this.s3 = s3;
	}

	private Show	show	= new Show();
	private Season	season	= new Season();
	private ISOLang	isoLang	= new ISOLang();

	private void visitAll(List<S3ObjectSummary> objectSummaries)
	{
		for (S3ObjectSummary awsObjSum : objectSummaries)
		{
			String key = awsObjSum.getKey();
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
		if (s3 == null)
		{
			initS3();
		}
		ObjectListing listing = s3.listObjects("givemeashowvideos");
		visitAll(listing.getObjectSummaries());
	}

	public void setGiveMeAShowProperties(GiveMeProperties givemeAShowProperties) {
		this.giveMeAShowProperties = givemeAShowProperties;

	}

	public void setInserter(Inserter inserter) {
		this.inserter = inserter;
	}

	public AmazonS3Client getS3() {
		return s3;
	}

}
