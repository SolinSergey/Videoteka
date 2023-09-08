import "./CodeVerificationPage.css"

const CodeVerificationPage = (props) => {
    const handleChange = (command) => {
        switch (command) {
            case '':
                props.setCommand(command)
                break
            case 'password':
                props.setCommand(command)
                break
            default:
                return null
        }
    }

    function sendCode() {
        handleChange('password')
    }    return(
        <div className={'code_verification__container'}>
            Введите код
        <button onClick={() => sendCode()}></button>
        </div>
    )
}

export default CodeVerificationPage