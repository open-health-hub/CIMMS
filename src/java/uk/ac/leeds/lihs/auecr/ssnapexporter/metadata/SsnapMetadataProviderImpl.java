package uk.ac.leeds.lihs.auecr.ssnapexporter.metadata;

public class SsnapMetadataProviderImpl implements SsnapMetadataProvider {

	private SsnapMetadata metadata;
	
	public SsnapMetadataProviderImpl(SsnapMetadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public SsnapMetadata getMetadata() {
		return this.metadata;
	}

}
