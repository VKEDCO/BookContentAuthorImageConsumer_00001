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
			if ( author_name.equals("rumi") ) {
				mApp.processBookAuthorImage(mApp.getBookAuthorImageByName(mRumiAuthor));
				if ( mApp.mBookAuthorImages.containsKey(mRumiAuthor) ) {
					mAuthorImage.setImageBitmap(mApp.mBookAuthorImages.get(mRumiAuthor));
				}
				else {
					Toast.makeText(mApp, "image retrieval failure", Toast.LENGTH_LONG).show();
				}
			}
			else if ( author_name.equals("hafiz") || author_name.equals("hafez") ) {
				mApp.processBookAuthorImage(mApp.getBookAuthorImageByName(mHafizAuthor));
				if ( mApp.mBookAuthorImages.containsKey(mHafizAuthor) ) {
					mAuthorImage.setImageBitmap(mApp.mBookAuthorImages.get(mHafizAuthor));
				}
				else {
					Toast.makeText(mApp, "image retrieval failure", Toast.LENGTH_LONG).show();
				}
			}
			return;
		case R.id.btnAuTitle:
			author_name = mEdTxtAuthor.getText().toString();
			title_name  = mEdTxtTitle.getText().toString();
			String author_param_value;
			String title_param_value;
			
			if ( author_name.equals("rumi") ) {
				author_param_value = mRumiAuthor;
			}
			else if ( author_name.equals("hafiz") || author_name.equals("hafez") ) {
				author_param_value = mHafizAuthor;
			}
			else {
				Toast.makeText(mApp, "unknown author", Toast.LENGTH_LONG).show();
				return;
			}
			
			if ( title_name.equals("essential rumi") ) {
				title_param_value = mEssentialRumi;
			}
			else if ( title_name.equals("illuminated rumi") ) {
				title_param_value = mIlluminatedRumi;
			}
			else if ( title_name.equals("year with rumi") ) {
				title_param_value = mYearWithRumi;
			}
			else if ( title_name.equals("year with hafiz") ) {
				title_param_value = mYearWithHafiz;
			}
			else if ( title_name.equals("the gift") ) {
				title_param_value = mTheGift;
			}
			else {
				Toast.makeText(mApp, "unknown title", Toast.LENGTH_LONG).show();
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
