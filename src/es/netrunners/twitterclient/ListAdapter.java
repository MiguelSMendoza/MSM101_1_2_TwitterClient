package es.netrunners.twitterclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class ListAdapter extends SimpleAdapter {

	ArrayList<HashMap<String, String>> items;

	@SuppressWarnings("unchecked")
	public ListAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.items = (ArrayList<HashMap<String, String>>) data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		final HashMap<String, String> item = items.get(position);
		if (item != null) {
			final ImageView image = (ImageView) view.findViewById(R.id.image);
			if (image != null) {
				try {
					// Almacenamos el hiperenlace con la clave Image en la
					// Actividad Principal
					final URL thumb_u = new URL(item.get("Image"));
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
							final Drawable thumb_d = Drawable
										.createFromStream(thumb_u.openStream(),
												"src");
							image.post(new Runnable() {
									@Override
									public void run() {
										image.setImageDrawable(thumb_d);
									}
								});
							} catch (MalformedURLException e) {

							} catch (IOException e) {

							}
						}
					}).start();

				} catch (Exception e) {
					// handle it
				}

			}
		}
		return view;
	}

}
