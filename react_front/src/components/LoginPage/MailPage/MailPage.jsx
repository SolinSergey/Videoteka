import "./MailPage.css"
import FlexBetween from "../../UI/FlexBetween";
import {Box, Button, Divider, TextField, Typography, useMediaQuery} from "@mui/material";
import {useState} from "react";
import axios from "axios";

const MailPage = (props) => {
    const isNonMobile = useMediaQuery("(min-width:600px)")
    const [email, setEmail] = useState('')
    const handleChangePage = (command) => {
        switch (command) {
            case '':
                props.setCommand(command)
                break
            case 'add_review':
                props.setCommand(command)
                break
            default:
                return null
        }
    }
    // String email;
    // String subject;
    // String firstName;
    // String message;
    function sendEmail() {
        handleChangePage('code')
    }

    const handleChange = (event) => {
        setEmail(event.target.value)
    }
    const handleSubmit = async () => {
        try {
            await axios.post('http://localhost:5555/auth/api/v1/users/password_attempt/?email=' + email).then(handleChangePage(props.step + 1))
        } catch (e) {

        }
    }

    return (
        <FlexBetween>
            <form onSubmit={handleSubmit}>
                <Box
                    display='flex'
                    flexDirection={'column'}
                    justifyContent={'center'}
                    alignItems={'center'}
                    mt={'10%'}
                    width={'100%'}
                >
                    <TextField
                        label='Почта'
                        onChange={handleChange}
                        value={email}
                        name='email'
                        sx={{
                            width: "100%",
                            height: "50px",
                            mb: "1rem"
                        }}
                    />

                    <Divider color={'#b0b0b0'} variant="middle" />

                    <Box width={'100%'} mt={'2rem'}>
                        <Typography
                            color={'#b0b0b0'}
                            align={'left'}
                            variant="body2"
                            gutterBottom
                        >
                            Мы не используем адрес электронной почты для рекламы и не передаём информацию пользователей третьим лицам.
                        </Typography>
                    </Box>

                    <Box width={'100%'} mt={'.5rem'}>
                        <Button
                            fullWidth
                            type='submit'
                            sx={{
                                m: "2rem 0",
                                p: "1rem",
                                width: '100%',
                                backgroundColor: "rgb(2,15,108)",
                                backgroundImage: "linear-gradient(11deg, rgba(2,15,108,1) 0%, rgba(16,53,138,1) 35%, rgba(62,74,179,1) 66%, rgba(0,151,255,1) 99%)",
                                color: "#b0b0b0",
                                "&:hover": {
                                    color: "white",
                                    backgroundColor: "rgb(2,15,108)",
                                    backgroundImage: "linear-gradient(5deg, rgba(2,15,108,1) 0%, rgba(16,53,138,1) 35%, rgba(62,74,179,1) 66%, rgba(0,151,255,1) 99%)"
                                },
                            }}>
                            ДАЛЕЕ
                        </Button>
                    </Box>
                </Box>
            </form>
        </FlexBetween>
    )
}

export default MailPage