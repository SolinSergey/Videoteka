import "./ModalWindow.css"
function ModalWindow({setCommand, active, setActive, children}) {
    const closeWindow = () => {
      setActive(false)
        setCommand('')
    }
    return(
        <div className={active ? 'modal active' : 'modal'} onClick={() => closeWindow()}>
            <div className={active? 'modal__content active' : 'modal__content'} onClick={event => event.stopPropagation()}>
                {children}
            </div>
        </div>
    )
}
export default ModalWindow;