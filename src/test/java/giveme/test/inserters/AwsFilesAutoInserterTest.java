package giveme.test.inserters;

import giveme.inserters.AwsFilesAutoInserter;
import giveme.inserters.Inserter;
import giveme.shared.GiveMeProperties;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AwsFilesAutoInserterTest {

	private static final Logger	LOGGER	= Logger.getLogger(AwsFilesAutoInserterTest.class
												.getName());

	AwsFilesAutoInserter	awsFilesAutoInserter;

	@Before
	public void init() throws Exception {
		awsFilesAutoInserter = new AwsFilesAutoInserter();
		AmazonS3Client amazonS3Mock = Mockito.mock(AmazonS3Client.class);
		
		ObjectListing returnListMock = Mockito.mock(ObjectListing.class);
		List<S3ObjectSummary> objctSummaries = new ArrayList<S3ObjectSummary>();
		S3ObjectSummary sum1 = new S3ObjectSummary();
		sum1.setKey("/American_dad");
		S3ObjectSummary sum2 = new S3ObjectSummary();
		sum2.setKey("/American_dad/Season_1/");
		S3ObjectSummary sum3 = new S3ObjectSummary();
		sum3.setKey("/American_dad/Season_1/fr");
		S3ObjectSummary sum4 = new S3ObjectSummary();
		sum4.setKey("/American_dad/Season_1/fr/video10.webm");
		S3ObjectSummary tooDeep = new S3ObjectSummary();
		tooDeep.setKey("/American_dad/Season_1/fr/video10/file.webm");
		objctSummaries.add(sum1);
		objctSummaries.add(sum2);
		objctSummaries.add(sum3);
		objctSummaries.add(sum4);
		objctSummaries.add(tooDeep);
		Mockito.doReturn(objctSummaries).when(returnListMock)
				.getObjectSummaries();
	
		Mockito.doReturn(returnListMock).when(amazonS3Mock)
				.listObjects(Mockito.anyString());
	
		awsFilesAutoInserter.setS3(amazonS3Mock);
		Inserter inserterMock = Mockito.mock(Inserter.class);
		awsFilesAutoInserter.setInserter(inserterMock);
	}

	@Test
	public void visitAllTest() {
		GiveMeProperties givemeAShowProperties = new GiveMeProperties();
		givemeAShowProperties.setAWKEY("key");
		givemeAShowProperties.setAWSECRET("secret");
		awsFilesAutoInserter.setGiveMeAShowProperties(givemeAShowProperties);
		awsFilesAutoInserter.visitAll();
		LOGGER.info("visit");
	}

	@Test
	public void initWithPropertiesToNull() {
		AwsFilesAutoInserter awsAuto = new AwsFilesAutoInserter();
		Assertions.assertThat(awsAuto.getS3()).isNull();
	}

	@Test
	public void initWithProperties() {
		GiveMeProperties givemeAShowProperties = new GiveMeProperties();
		givemeAShowProperties.setAWKEY("key");
		givemeAShowProperties.setAWSECRET("secret");
		awsFilesAutoInserter.setGiveMeAShowProperties(givemeAShowProperties);
		awsFilesAutoInserter.init();
		Assertions.assertThat(awsFilesAutoInserter.getS3()).isNotNull();
	}
}
