package org.vkedco.mobappdev.book_content_author_image_consumer_00001;

/**
 ****************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ****************************************************
 */

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BookContentAuthorImageAct extends Activity
	implements OnClickListener
{
	
	// author constants
	final String RUMI = "rumi";
	final String HAFIZ = "hafiz";
	final String HAFEZ = "hafez";
	
	// title constants
	final String ESSENTIAL_RUMI_TITLE = "essential rumi";
	final String ILLUMINATED_RUMI_TITLE = "illuminated rumi";
	final String YEAR_WITH_RUMI_TITLE = "year with rumi";
	final String YEAR_WITH_HAFIZ_TITLE = "year with hafiz";
	final String THE_GIFT_TITLE = "the gift";
	
	// failure constants
	final String IMAGE_RETRIEVAL_FAILURE = "image retrieval failure";
	final String UNKNOWN_AUTHOR_FAILURE = "unknown author";
	final String UNKNOWN_TITLE_FAILURE = "unknown title";
	
	String[] mAuthors = null;
	String[] mTitles  = null;
	EditText mEdTxtAuthor = null;
	EditText mEdTxtTitle  = null;
	Button   mBtnAuthor   = null;
	Button   mBtnAuthorTitle = null;
	Button   mBtnClear = null;
	String mRumiAuthor;
	String mHafizAuthor;
	String mEssentialRumi;
	String mIlluminatedRumi;
	String mYearWithRumi;
	String mYearWithHafiz;
	String mTheGift;
	ImageView mAuthorImage = null;
	ImageView mCoverImage = null;
	BookContentAuthorImageConsumerApp mApp = null;
	static final String LOGTAG = BookContentAuthorImageAct.class.getSimpleName() + "_LOG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_content_author_image_activity_layout);
        mAuthors = getResources().getStringArray(R.array.book_authors);
        mTitles  = getResources().getStringArray(R.array.book_titles);
        mRumiAuthor 		= mAuthors[0];
        mHafizAuthor 		= mAuthors[1];
        mEssentialRumi 		= mTitles[0];
        mIlluminatedRumi 	= mTitles[1];
        mYearWithRumi 		= mTitles[2];
        mYearWithHafiz		= mTitles[3];
        mTheGift			= mTitles[4];
        
        mEdTxtAuthor = (EditText) this.findViewById(R.id.edTxtAuthor);
        mEdTxtTitle  = (EditText) this.findViewById(R.id.edTxtTitle);
        mBtnAuthor   = (Button) this.findViewById(R.id.btnAuthor);
        mBtnAuthorTitle = (Button) this.findViewById(R.id.btnAuTitle);
        mBtnClear = (Button) this.findViewById(R.id.btnClear);
        
        mBtnAuthor.setOnClickListener(this);
        mBtnAuthorTitle.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        
        mAuthorImage = (ImageView) this.findViewById(R.id.img_view_author);
        mCoverImage = (ImageView) this.findViewById(R.id.img_view_cover);
        
        mApp = (BookContentAuthorImageConsumerApp) this.getApplication();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_content_author_image_activity_layout, menu);
        return true;
    }

	
	@Override
	public void onClick(View v) {
		String author_name;
		String title_name;
		switch ( v.getId() ) {
		case R.id.btnAuthor:
			author_name = mEdTxtAuthor.getText().toString();
			if ( author_name.equals(RUMI) ) {
				mApp.processBookAuthorImage(mApp.getBookAuthorImageByName(mRumiAuthor));
				if ( mApp.mBookAuthorImages.containsKey(mRumiAuthor) ) {
					mAuthorImage.setImageBitmap(mApp.mBookAuthorImages.get(mRumiAuthor));
				}
				else {
					Toast.makeText(mApp, IMAGE_RETRIEVAL_FAILURE, Toast.LENGTH_LONG).show();
				}
			}
			else if ( author_name.equals(HAFIZ) || author_name.equals(HAFEZ) ) {
				mApp.processBookAuthorImage(mApp.getBookAuthorImageByName(mHafizAuthor));
				if ( mApp.mBookAuthorImages.containsKey(mHafizAuthor) ) {
					mAuthorImage.setImageBitmap(mApp.mBookAuthorImages.get(mHafizAuthor));
				}
				else {
					Toast.makeText(mApp, IMAGE_RETRIEVAL_FAILURE, Toast.LENGTH_LONG).show();
				}
			}
			return;
		case R.id.btnAuTitle:
			author_name = mEdTxtAuthor.getText().toString();
			title_name  = mEdTxtTitle.getText().toString();
			String author_param_value;
			String title_param_value;
			
			if ( author_name.equals(RUMI) ) {
				author_param_value = mRumiAuthor;
			}
			else if ( author_name.equals(HAFIZ) || author_name.equals(HAFEZ) ) {
				author_param_value = mHafizAuthor;
			}
			else {
				Toast.makeText(mApp, UNKNOWN_AUTHOR_FAILURE, Toast.LENGTH_LONG).show();
				return;
			}
			
			if ( title_name.equals(ESSENTIAL_RUMI_TITLE) ) {
				title_param_value = mEssentialRumi;
			}
			else if ( title_name.equals(ILLUMINATED_RUMI_TITLE) ) {
				title_param_value = mIlluminatedRumi;
			}
			else if ( title_name.equals(YEAR_WITH_RUMI_TITLE) ) {
				title_param_value = mYearWithRumi;
			}
			else if ( title_name.equals(YEAR_WITH_HAFIZ_TITLE) ) {
				title_param_value = mYearWithHafiz;
			}
			else if ( title_name.equals(THE_GIFT_TITLE) ) {
				title_param_value = mTheGift;
			}
			else {
				Toast.makeText(mApp, UNKNOWN_TITLE_FAILURE, Toast.LENGTH_LONG).show();
				return;
			}
			
			
			mApp.processBookAuthorImage(mApp.getBookAuthorImageByName(author_param_value));
			if ( mApp.mBookAuthorImages.containsKey(author_param_value) ) {
				mAuthorImage.setImageBitmap(mApp.mBookAuthorImages.get(author_param_value));
			}
			else {
				Toast.makeText(mApp, "author image retrieval failure", Toast.LENGTH_LONG).show();
				Log.d(LOGTAG, "author image retrieval failure on " + author_param_value);
			}
			
			mApp.processBookCoverImage(mApp.getBookCoverImageByTitle(title_param_value), title_param_value);
			if ( mApp.mBookCoverImages.containsKey(title_param_value) ) {
				mCoverImage.setImageBitmap(mApp.mBookCoverImages.get(title_param_value));
			}
			else {
				Toast.makeText(mApp, "cover image retrieval failure", Toast.LENGTH_LONG).show();
				Log.d(LOGTAG, "cover image retrieval failure on " + title_param_value);
			}
			return;
		case R.id.btnClear:
			this.mEdTxtAuthor.setText("");
			this.mEdTxtTitle.setText("");
			return;
		default:
			return;
		}
	}
}
