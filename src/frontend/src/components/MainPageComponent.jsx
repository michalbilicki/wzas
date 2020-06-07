import * as React from "react";

import '../resources/css/main.css'
import AppBar from "@material-ui/core/AppBar";
import Paper from "@material-ui/core/Paper";
import TableCell from "@material-ui/core/TableCell";
import Grid from "@material-ui/core/Grid";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableBody from "@material-ui/core/TableBody";
import ButtonMenuComponent from "./ButtonMenuComponent";
import Card from "@material-ui/core/Card";
import axios from "axios";
import withStyles from "@material-ui/core/styles/withStyles";
import TextField from "@material-ui/core/TextField";


const CssTextField = withStyles({
    root: {
        '& label.Mui-focused': {
            color: 'white'
        },
        '& .MuiInput-underline:after': {
            borderBottomColor: 'white',
            color: 'white'
        },
        '& .MuiOutlinedInput-root': {
            '& fieldset': {
                borderColor: 'white',
                color: 'white'
            },
            '&:hover fieldset': {
                borderColor: 'white',
                color: 'white'
            },
            '&.Mui-focused fieldset': {
                borderColor: 'white',
                color: 'white'
            },
        },
        '&.MuiOutlinedInput-root' : {
            color: 'white'
        }
    },
})(TextField);

class MainPageComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: null,
            removeLoading: false,
            createLoading: false,
            tableLoading: true,
            amountOfPoints: 0
        }
    }

    getHeaders = () => {
        return {
            "authorization": localStorage.getItem("TOKEN")
        }
    };

    componentDidMount() {
        this.loadData();
    }

    loadData = () => {
        axios.get('/api/location/', {
            headers: this.getHeaders()
        })
            .then(response => {
                this.setState({
                    value: response.data
                })
            }).finally(() => {
            this.setState({tableLoading: false});
            console.log(this.state.value);
        });
    };

    createTestPoints = () => {
        this.setState({
            createLoading: true
        });

        axios.post(`/api/admin/create-test-data?amount=${this.state.amountOfPoints}`, null, {
            headers: this.getHeaders()
        })
            .then(response => {
                return response
            }).finally(() => {
            this.loadData();
            this.setState({
                createLoading: false,
                setAnchorEl: null
            });
        });
    };

    deleteTestPoints = () => {
        this.setState({
            removeLoading: true
        });

        axios.delete('/api/admin/delete', {
            headers: this.getHeaders()
        }).then(response => {
            return response;
        }).finally(() => {
            this.loadData();
            this.setState({
                removeLoading: false,
                setAnchorEl: null
            });
        });
    };

    handleChange = (newValue) => {
        this.setState({
            value: newValue
        });
    };

    getTableBody = () => {
        return (
            this.state.value.map((row) => (
                <TableRow key={row.account}>
                    <TableCell component="th" scope="row">{row.account}</TableCell>
                    <TableCell align="right">{row.current.latitude}</TableCell>
                    <TableCell align="right">{row.current.longitude}</TableCell>
                    <TableCell align="right">{row.destination.latitude}</TableCell>
                    <TableCell align="right">{row.destination.longitude}</TableCell>
                </TableRow>
            ))
        );
    };

    clearLocalStorageAndCookies = () => {
        localStorage.clear();
        window.location.href = "/loginpage";
    };

    changeAmountOfPoints = (event) => {
        this.setState({
            amountOfPoints: event.target.value
        })
    };


    render() {
        return (
            <Grid container
                  justify="center"
                  alignItems="flex-start">
                <Grid container xs={12}>
                    <AppBar position="static">
                        <Toolbar>
                            <Grid container
                                  direction='row'
                                  justify='space-between'
                                  alignItems="center">
                                <Grid container xs={3}
                                      justify="flex-start"
                                      alignItems="center">
                                    <ButtonMenuComponent createTestPoints={this.createTestPoints}
                                                         deleteTestPoints={this.deleteTestPoints}
                                                         removeLoading={this.state.removeLoading}
                                                         createLoading={this.state.createLoading}/>
                                </Grid>
                                <Grid container xs={6}
                                      justify="center"
                                      alignItems="center">
                                    <Typography variant="h4">
                                        Admin page
                                    </Typography>
                                </Grid>
                                <Grid container xs={3}
                                      justify="flex-end"
                                      alignItems="center">
                                    <Button variant="contained"
                                            color="secondary"
                                            onClick={this.clearLocalStorageAndCookies}>
                                        LOGOUT
                                    </Button>
                                </Grid>
                            </Grid>
                        </Toolbar>
                    </AppBar>
                </Grid>
                <Grid container xs={11}
                      justify="center"
                      alignItems="flex-start"
                      style={{padding: 20}}>
                    {this.state.value !== null && this.state.value.length !== 0 ?
                        <TableContainer component={Paper} style={{padding: 10}}>
                            <Table size="large" aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell><b>Account</b></TableCell>
                                        <TableCell><b>Location (x)</b></TableCell>
                                        <TableCell><b>Location (y)</b></TableCell>
                                        <TableCell><b>Destination (x)</b></TableCell>
                                        <TableCell><b>Destination (y)</b></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.value.map((row) => (
                                        <TableRow key={row.account}>
                                            {row.account === null ?
                                                <TableCell component="th" scope="row">empty</TableCell> :
                                                <TableCell component="th" scope="row">{row.account}</TableCell>}
                                            <TableCell align="left">{row.current.latitude}</TableCell>
                                            <TableCell align="left">{row.current.longitude}</TableCell>
                                            {row.destination === null ? <TableCell align="left">empty</TableCell>
                                                : <TableCell align="left">{row.destination.latitude}</TableCell>
                                            }
                                            {row.destination === null ? <TableCell align="left">empty</TableCell>
                                                : <TableCell align="left">{row.destination.longitude}</TableCell>
                                            }
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer> :
                        <Card style={{padding: 10}}>
                            <Typography>
                                EMPTY TABLE POINTS
                            </Typography>
                        </Card>
                    }
                        <CssTextField
                            label={"Amount of test points"}
                            value={this.state.amountOfPoints}
                            name={"amountOfPoints"}
                            fullWidth
                            variant="outlined"
                            margin={"normal"}
                            onChange={this.changeAmountOfPoints}
                            style={{
                                borderColor: "white"
                            }}
                            InputProps={{
                                style: {
                                    color: "white",
                                    borderColor: 'red',
                                }
                            }}
                            InputLabelProps={{
                                style: {
                                    color: "white"
                                }
                            }}/>

                </Grid>
            </Grid>
        );
    }
}

export default MainPageComponent;

