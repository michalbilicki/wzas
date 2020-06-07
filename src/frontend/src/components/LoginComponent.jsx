import * as React from "react";
import '../resources/css/main.css'
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import Typography from "@material-ui/core/Typography";
import axios from "axios";


class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: "",
            password: ""
        };

    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const body = {
            login: this.state.login,
            password: this.state.password
        };

        axios.post('/login', body)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    this.props.onSuccessfulLogin(response.data, response.headers.authorization);
                } else {
                    window.alert("Błędny login lub hasło")
                }
            }).catch(e => {
        });

    };

    render() {
        return (
            <div className="background-login-page">
                <Grid container>
                    <Container maxWidth="sm" m="auto">
                        <div className="login-form">
                            <Typography component="h1" variant="h4">
                                Logowanie
                            </Typography>
                            <form onSubmit={this.handleSubmit}>
                                <TextField style={{background: "lightgrey"}}
                                           variant="filled"
                                           margin="normal"
                                           fullWidth
                                           name="login"
                                           label="Login"
                                           onChange={this.handleChange}
                                           required
                                           inputProps={{
                                               maxLength: 20
                                           }}
                                />
                                <TextField style={{background: "lightgrey"}}
                                           type="password"
                                           variant="filled"
                                           margin="normal"
                                           fullWidth
                                           onChange={this.handleChange}
                                           name="password"
                                           label="Hasło"
                                           required
                                />
                                <Button
                                    name="signInButton"
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    color={"primary"}>
                                    Login
                                </Button>
                            </form>
                        </div>
                    </Container>
                </Grid>
            </div>
        )
    }


}

export default LoginComponent