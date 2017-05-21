## SETUP GUIDE

1) app_bar_main.xml gives the game_name, profile picture, and game_description.

MainActivity

LoadNavHead() is for loading the profile,background,game_name,game_description

setUpNavigationView()
for setting up the menu item to get open

getSupportFragmentManager
This does an interesting job so when ever we click the menu framgment manager stores the fragment under a tag and loads the fragment. benifit if we have to check if the fragment is visited we can check from the tag name itself

_____

## PROBLEM FACED

1)  
So first when I was dealing with fragments the problem was I didnt understood the concept of fragments and thought as a simple layout. So there was a situation when I had to replace one fragment with another but the problem was this is it be done on click button. Now coding point of view if I have to make changes on the fragment I have to do it in the mainActivity itself but if I am doing inside the java class it was not working + this should not be the coding style as it can create problem later.

But still to complete the task quickly I did something like this inside the OnCreateView()

```
final LinearLayout mL = (LinearLayout) inflater.inflate(R.layout.main_fragment, container, false);
        final Button button = (Button) mL.findViewById(R.id.play);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LayoutInflater x = LayoutInflater.from(getContext());
                View v = x.inflate(R.layout.concentration, null);
                View parent = (View) view.getParent();

                mL.removeView(parent.findViewById(R.id.game_name));
                mL.removeView(parent.findViewById(R.id.play));
                mL.addView(v);
                button.setClickable(false);
            }
        });
        return mL;
```

Then I later realised rather than doing this stupidity I have a solution ready in the mainActivity. So this was my next procedure..

I was saving the button view on a variable and was accessing that variable as a member of the MainFragment inside the mainActivity but there was an issue in the thought.. I was not considering that after the drawer is closed than the fragment is set to view. 

After realising this fault, now I came to the same previous code and added fragmenttransaction code inside the button onclick and IT IS WORKING.. now thinking why didnt I do it earlier :P


2)

This was the problem of how to send the data to the server and also the approach should be able to handle dynamicity and also clean code.

So to start thinking I created a JSONObject class(StoreOnClick) but the problem is now to make the class robust so that it can handle query efficiently and make json.

So then I thought of a general structure of a json as

{
	"level" :[
			{
				"score":
				"wrong_answer":
				"correct_answer":
				"click_details": [{"time_of_click": ,"status_of_click":},
						  {}]
			},
			{
			}
		],

	"extras" :{
			
		}
}

and now I will create two functions insert_level_data, insert_extra_data

This is a begining thought let see how to pull for more complicated situations later!

3) This was an error which I faced while I was updating the Setting page, So now I am trying to install material design in my setting page. first problem was how to change the color of the editView text --> hint, linebar so for that I searched and finally concluded can be done intresting by adding a style for the editView.

the style was:

```
<style name="EditText" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorAccent">@android:color/white</item>
        <item name="colorPrimary">@android:color/white</item>
        <item name="colorPrimaryDark">@android:color/white</item>
        <item name="android:background">@null</item>
        <item name="android:windowTranslucentStatus">true</item>

        <item name="android:textColorHint">@color/white</item>
        <item name="android:textColor">@color/white</item>
        <item name="colorControlActivated">@color/white</item>
        <item name="colorControlNormal">@color/aluminum</item>
        <item name="colorControlHighlight">@color/white</item>
    </style>
```

and this wes set on the parent tag so that the colorAccent, colorPrimary, colorPrimaryDark can be in effect.

Having this problem solved there was another problem and it was whenever I tried to edit the text, but background used to shrink and take the space between the OnScreenKeyboard and the top bar. This problem was resolved as follows:

```
android:windowSoftInputMode="stateHidden|adjustResize|adjustPan"
```

add this on the activity which has the following activity scroll is enabled and VOILA IT WORKS :).

But then having resolved this there was another problem as the keyboard was opening, the Edittext was getting hidden behind the OnScreenKeyboard the solution was simple though I added two lines in the Layout consisting of the button

```
android:layout_weight="1"
android:paddingTop="70dp"
```

This created enough vertical space for all the texts to show up.

