import React, {useEffect} from "react";
import { guid } from '@progress/kendo-react-common';
import { timezoneNames } from '@progress/kendo-date-math';
import { DropDownList } from '@progress/kendo-react-dropdowns';
import { IntlProvider, load, LocalizationProvider, loadMessages } from '@progress/kendo-react-intl';
import { Scheduler, TimelineView, DayView, WeekView, MonthView, AgendaView } from '@progress/kendo-react-scheduler';
import weekData from 'cldr-core/supplemental/weekData.json';
import currencyData from 'cldr-core/supplemental/currencyData.json';
import likelySubtags from 'cldr-core/supplemental/likelySubtags.json';
import numbers from 'cldr-numbers-full/main/es/numbers.json';
import dateFields from 'cldr-dates-full/main/es/dateFields.json';
import currencies from 'cldr-numbers-full/main/es/currencies.json';
import caGregorian from 'cldr-dates-full/main/es/ca-gregorian.json';
import timeZoneNames from 'cldr-dates-full/main/es/timeZoneNames.json';
import '@progress/kendo-date-math/tz/Etc/UTC';
import '@progress/kendo-date-math/tz/Europe/Sofia';
import '@progress/kendo-date-math/tz/Europe/Madrid';
import '@progress/kendo-date-math/tz/Asia/Dubai';
import '@progress/kendo-date-math/tz/Asia/Tokyo';
import '@progress/kendo-date-math/tz/America/New_York';
import '@progress/kendo-date-math/tz/America/Los_Angeles';
import esMessages from './es.json';

import '../styling/Itinerary.css';

load(likelySubtags, currencyData, weekData, numbers, currencies, caGregorian, dateFields, timeZoneNames);
loadMessages(esMessages, 'es-ES');

const Itinerary = ({ trip }) => {

  const customModelFields = {
    id: 'TaskID',
    title: 'Title',
    description: 'Description',
    start: 'Start',
    end: 'End',
    recurrenceRule: 'RecurrenceRule',
    recurrenceId: 'RecurrenceID',
    recurrenceExceptions: 'RecurrenceException'
  };

  let year = new Date().getFullYear();
  let month = new Date().getMonth();
  let day = new Date().getDate();

  const parseAdjust = eventDate => {
    const date = new Date(eventDate);
    date.setFullYear(year);
    return date;
  };
  
  let displayDate = new Date(Date.UTC(year, month, day));

  let activitiesSchema = null;

  const timezones = React.useMemo(() => timezoneNames(), []);
  const locales = [{
    language: 'en-US',
    locale: 'en'
  }, {
    language: 'es-ES',
    locale: 'es'
  }];
  const [view, setView] = React.useState('day');
  const [date, setDate] = React.useState(displayDate);
  const [locale, setLocale] = React.useState(locales[0]);
  const [timezone, setTimezone] = React.useState('America/Los_Angeles');
  const [orientation, setOrientation] = React.useState('horizontal');
  const [data, setData] = React.useState(activitiesSchema);
  const handleViewChange = React.useCallback(event => {
    setView(event.value);
  }, [setView]);
  const handleDateChange = React.useCallback(event => {
    setDate(event.value);
  }, [setDate]);
  const handleLocaleChange = React.useCallback(event => {
    setLocale(event.target.value);
  }, [setLocale]);
  const handleTimezoneChange = React.useCallback(event => {
    setTimezone(event.target.value);
  }, [setTimezone]);
  const handleOrientationChange = React.useCallback(event => {
    setOrientation(event.target.getAttribute('data-orientation'));
  }, []);
  const handleDataChange = React.useCallback(({
    created,
    updated,
    deleted
  }) => {
    setData(old => old
      .filter(item => deleted.find(current => current.TaskID === item.TaskID) === undefined)
      .map(item => updated.find(current => current.TaskID === item.TaskID) || item)
      .concat(created.map(item => Object.assign({}, item, {
      TaskID: guid()
    }))));
  }, [setData]);

  useEffect(() => {
    if (trip && date) {
      activitiesSchema = trip.activities_booked.map(dataItem => ({ ...dataItem,
        Start: parseAdjust(dataItem.start),
        End: parseAdjust(dataItem.end),
        TaskID: dataItem.id,
        Title: dataItem.activity
      }));
      setData(activitiesSchema)
    }
  }, [trip, date]);

  return (<div className= "App">
          <div className="Itinerary">
          <div className="example-config">
            <div className="row">
              <div className="col">
                <h5>Timezone:</h5>
                <DropDownList value={timezone} onChange={handleTimezoneChange} data={timezones} />
              </div>
              <div className="col">
                <h5>Locale:</h5>
                <DropDownList value={locale} onChange={handleLocaleChange} data={locales} textField="language" dataItemKey="locale" />
              </div>
              <div className="col">
                <h5>Orientation:</h5>
                <input type="radio" name="orientation" id="horizontal" data-orientation="horizontal" className="k-radio k-radio-md" checked={orientation === 'horizontal'} onChange={handleOrientationChange} />
                <label className="k-radio-label" htmlFor="horizontal">Horizontal</label>
                <br />
                <input type="radio" name="orientation" id="vertical" data-orientation="vertical" className="k-radio k-radio-md" checked={orientation === 'vertical'} onChange={handleOrientationChange} />
                <label className="k-radio-label" htmlFor="vertical">Vertical</label>
              </div>
            </div>
          </div>
          <LocalizationProvider language={locale.language}>
            <IntlProvider locale={locale.locale}>
              <Scheduler data={data} onDataChange={handleDataChange} view={view} onViewChange={handleViewChange} date={date} onDateChange={handleDateChange} editable={false} timezone={timezone} modelFields={customModelFields} group={{
            resources: ['Trips'],
            orientation
          }} resources={[{
            name: 'Trips',
            data: [{
              text: 'Activities',
              value: 1
            }],
            valueField: 'value',
            textField: 'text',
            colorField: 'color'
          }]}>
                <TimelineView />
                <DayView />
                <WeekView />
                <MonthView />
                <AgendaView />
              </Scheduler>
            </IntlProvider>
          </LocalizationProvider>
          </div>
      </div>
      )
};

export default Itinerary;