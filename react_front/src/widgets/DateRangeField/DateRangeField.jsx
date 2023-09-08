import style from './DataRangeField.module.css'
function DateRangeField(props) {


    return (
        <div className={style.container}>
            <div className={style.field}>
                <label>с</label>
                <input type={'number'} className={style.start__field}  value={props.startYear}/>
            </div>
            <div className={style.separator}><span>-</span></div>
            <div className={style.field}>
                <label>по</label>
                <input type={'number'} className={style.end__field} value={props.endYear}/>
            </div>
        </div>
    );
}
export default DateRangeField;