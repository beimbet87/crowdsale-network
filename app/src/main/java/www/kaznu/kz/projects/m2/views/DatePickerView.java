package www.kaznu.kz.projects.m2.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import www.kaznu.kz.projects.m2.R;

public class DatePickerView extends LinearLayout
{
    // for logging
    private static final String LOGTAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "M";

    public int start = 0, end = 0;
    public int startIndex, endIndex;

    private String monthLabel [] = new String[] {
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
    };

    private String monthShortLabel [] = new String[] {
            "янв",
            "фев",
            "мар",
            "апр",
            "май",
            "июн",
            "июл",
            "авг",
            "сен",
            "окт",
            "ноя",
            "дек"
    };

    private String dateFormat;

    private Calendar currentDate = Calendar.getInstance();

    private EventHandler eventHandler = null;

    private TextView btnPrev;
    private TextView btnNext;
    private TextView txtDate;
    private GridView grid;
    private TextView tvStartDate;
    private TextView tvEndDate;

    Calendar next, prev;

    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public DatePickerView(Context context)
    {
        super(context);
    }

    public DatePickerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl(context, attrs);
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_control, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs)
    {
        dateFormat = DATE_FORMAT;
    }
    private void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        btnPrev = (TextView)findViewById(R.id.calendar_prev);
        btnNext = (TextView)findViewById(R.id.calendar_next);
        txtDate = (TextView)findViewById(R.id.calendar_current);
        grid = (GridView)findViewById(R.id.calendar_grid);
        tvStartDate = findViewById(R.id.tv_start_date);
        tvEndDate = findViewById(R.id.tv_end_date);

    }

    private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
            {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
                return true;
            }
        });

        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                if(start == 0 && end == 0) {
                    view.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));
                    start = 1;
                    end = 0;
                    startIndex = i;
                    tvStartDate.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tvStartDate.setTextColor(getResources().getColor(android.R.color.white));
                    tvStartDate.setText(sdf.format(adapterView.getItemAtPosition(startIndex)));
                }
                else if(start == 1 && end == 0) {
                    view.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));
                    start = 1;
                    end = 1;
                    endIndex = i;

                    tvEndDate.setBackground(getResources().getDrawable(R.drawable.selected_date));
                    tvEndDate.setTextColor(getResources().getColor(android.R.color.white));
                    tvEndDate.setText(sdf.format(adapterView.getItemAtPosition(endIndex)));
                    View dates;
                    adapterView.getChildAt(startIndex).setBackground(getResources().getDrawable(R.drawable.selected_date_range_start));
                    adapterView.getChildAt(endIndex).setBackground(getResources().getDrawable(R.drawable.selected_date_range_end));
                    for(int index = startIndex+1; index < endIndex; index++) {
                        dates = adapterView.getChildAt(index);
                        dates.setBackground(getResources().getDrawable(R.drawable.selected_dates));
                        if((index + 1) % 7 == 0) {
                            dates.setBackground(getResources().getDrawable(R.drawable.selected_dates_end));
                        }
                        else if(index % 7 == 0) {
                            dates.setBackground(getResources().getDrawable(R.drawable.selected_dates_start));
                        }
//                        ViewGroup.LayoutParams params = dates.getLayoutParams();
//                        params.height = getResources().getDimensionPixelSize(R.dimen.calendar_interval_size);
//
//                        dates.setLayoutParams(params);
                    }
                }
                else if(start == 1 && end == 1) {

                }
            }
        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events)
    {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK)-2;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(monthLabel[Integer.parseInt(sdf.format(currentDate.getTime()))-1]);
        prev = Calendar.getInstance();
        prev.setTime(currentDate.getTime());
        prev.add(Calendar.MONTH, -1);
        btnPrev.setText(monthLabel[Integer.parseInt(sdf.format(prev.getTime()))-1]);

        next = Calendar.getInstance();
        next.setTime(currentDate.getTime());
        next.add(Calendar.MONTH, +1);
        btnNext.setText(monthLabel[Integer.parseInt(sdf.format(next.getTime()))-1]);

        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));
    }


    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
        {
            super(context, R.layout.calendar_control_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // day in question
            Date date = getItem(position);

            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.calendar_control_day, parent, false);

            // if this day has an event, specify event image
            view.setBackgroundResource(0);

            ((TextView)view).setWidth(getResources().getDimensionPixelSize(R.dimen.calendar_date_size));
            ((TextView)view).setHeight(getResources().getDimensionPixelSize(R.dimen.calendar_date_size));
            ((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView)view).setTextColor(getResources().getColor(R.color.color_primary_dark));
            ((TextView)view).setTextSize(16);
            ((TextView)view).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            if (month == next.getTime().getMonth() || month == prev.getTime().getMonth())
            {
                // if this day is outside current month, grey it out
                ((TextView)view).setTextColor(getResources().getColor(R.color.color_primary_hint));
                ((TextView)view).setVisibility(INVISIBLE);
            }
            else if (day == today.getDate() && month == today.getMonth() && year == today.getYear())
            {
                // if it is today, set it to blue/bold
                ((TextView)view).setTypeface(null, Typeface.BOLD);
                ((TextView)view).setTextColor(getResources().getColor(R.color.color_primary_dark));
                ((TextView)view).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            }

            // set text
            ((TextView)view).setText(String.valueOf(date.getDate()));

            return view;
        }
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler
    {
        void onDayLongPress(Date date);
    }
}