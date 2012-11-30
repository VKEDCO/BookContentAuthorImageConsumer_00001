package org.vkedco.mobappdev.book_content_author_image_consumer_00001;

/**
 ****************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ****************************************************
 */

import java.util.HashMap;
import android.app.Application;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

public class BookContentAuthorImageConsumerApp extends Application {

	static final String NEWLINE = "\n";
	static final String FORWARD_SLASH = "/";
	static final String EQLS = "=";
	static final String LOGTAG = BookContentAuthorImageConsumerApp.class
			.getSimpleName() + "_LOG";
	static final String AUTHOR_PARAMETER = "author";
	static final String TITLE_PARAMETER = "title";
	static final String AMPERSAND = "&";

	// ********** book_cover_image table constants ******************
	// constants for book_cover_image column names
	static final String BOOK_COVER_IMAGE_TBL_ID_COL_NAME = "ID";
	static final String BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME = "ISBN";
	static final String BOOK_COVER_IMAGE_TBL_IMG_COL_NAME = "CoverIMG";
	static final String[] BOOK_COVER_IMAGE_TBL_COL_NAMES = {
			BOOK_COVER_IMAGE_TBL_ID_COL_NAME,
			BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME,
			BOOK_COVER_IMAGE_TBL_IMG_COL_NAME };

	// constants for book cover image column numbers
	static final int BOOK_COVER_IMAGE_TBL_ID_COL_NUM = 0;
	static final int BOOK_COVER_IMAGE_TBL_ISBN_COL_NUM = 1;
	static final int BOOK_COVER_IMAGE_TBL_IMG_COL_NUM = 2;

	// ********** book_author_image table constants ******************
	// constants for book_author_image column names
	static final String BOOK_AUTHOR_IMAGE_TBL_ID_COL_NAME = "ID";
	static final String BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME = "AuthorName";
	static final String BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME = "AuthorIMG";
	static final String[] BOOK_AUTHOR_IMAGE_TBL_COL_NAMES = {
			BOOK_AUTHOR_IMAGE_TBL_ID_COL_NAME,
			BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME,
			BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME };

	// constants for book author image column numbers
	static final int BOOK_AUTHOR_IMAGE_TBL_ID_COL_NUM = 0;
	static final int BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NUM = 1;
	static final int BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NUM = 2;

	HashMap<String, Bitmap> mBookAuthorImages = null;
	HashMap<String, Bitmap> mBookCoverImages = null;

	public BookContentAuthorImageConsumerApp() {
		mBookAuthorImages = new HashMap<String, Bitmap>();
		mBookCoverImages = new HashMap<String, Bitmap>();
	}

	Cursor getBookAuthorImageByName(String author_name) {
		if (mBookAuthorImages.containsKey(author_name))
			return null;

		Uri uri = Uri.parse(getResources().getString(
				R.string.uri_book_author_img_query)
				+ AUTHOR_PARAMETER + EQLS + author_name);
		Cursor rslt = getContentResolver()
				.query(uri,
						BookContentAuthorImageConsumerApp.BOOK_AUTHOR_IMAGE_TBL_COL_NAMES,
						null, null, null);
		return rslt;
	}

	Cursor getBookCoverImageByTitle(String title_name) {
		if (mBookCoverImages.containsKey(title_name))
			return null;
		Uri uri = Uri.parse(getResources().getString(
				R.string.uri_book_cover_img_query)
				+ TITLE_PARAMETER + EQLS + title_name);
		Cursor rslt = getContentResolver().query(uri, null, null, null, null);
		return rslt;
	}

	// process all book cover images
	void processBookAuthorImage(Cursor rslt) {
		if (rslt == null)
			return;

		if (rslt.getCount() != 0) {
			rslt.moveToFirst();
			String author_name = null;
			byte[] image_bytes = null;
			int id;
			while (rslt.isAfterLast() == false) {
				id = rslt
						.getInt(rslt
								.getColumnIndex(BookContentAuthorImageConsumerApp.BOOK_AUTHOR_IMAGE_TBL_ID_COL_NAME));
				author_name = rslt
						.getString(rslt
								.getColumnIndex(BookContentAuthorImageConsumerApp.BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME));
				if (!mBookAuthorImages.containsKey(author_name)) {
					image_bytes = rslt
							.getBlob(rslt
									.getColumnIndex(BookContentAuthorImageConsumerApp.BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME));
					mBookAuthorImages.put(author_name,
							BitmapFactory.decodeByteArray(image_bytes, 0,
									image_bytes.length));
					Log.d(LOGTAG, "bitmap created for author " + author_name);
				} else {
					Log.d(LOGTAG, "bitmap already exists for author "
							+ author_name);
				}

				rslt.moveToNext();
			}
		} else {
			Log.d(LOGTAG, "zero entries in cursor");
		}

		rslt.close();
	}

	// process all book cover images
	void processBookCoverImage(Cursor rslt, String title_name) {
		if (rslt == null)
			return;

		if (rslt.getCount() != 0) {
			rslt.moveToFirst();
			int id = 0;
			String isbn = null;
			byte[] image_bytes = null;

			id = rslt
					.getInt(rslt
							.getColumnIndex(BookContentAuthorImageConsumerApp.BOOK_COVER_IMAGE_TBL_ID_COL_NAME));
			isbn = rslt
					.getString(rslt
							.getColumnIndex(BookContentAuthorImageConsumerApp.BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME));

			image_bytes = rslt
					.getBlob(rslt
							.getColumnIndex(BookContentAuthorImageConsumerApp.BOOK_COVER_IMAGE_TBL_IMG_COL_NAME));
			mBookCoverImages.put(title_name, BitmapFactory.decodeByteArray(
					image_bytes, 0, image_bytes.length));
			Log.d(LOGTAG, "bitmap created for title " + title_name);
		} else {
			Log.d(LOGTAG, "zero entries in cursor");
		}

		rslt.close();
	}

}
