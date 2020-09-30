# Page Structure


# Database Structure
1. Database  
- users(uid)
```json
{
    "email":"", 
    "userid":"",
    "username":"", 
    "extraInfo":"" 
}
```
- location(uid)
```json
{
    "lat":"",//double
    "lng":"" //double
}
```
- run_records(uid)
```json
(runId)//Different run id should be generated
{
    "time":"",
    "distance":"",
    "mean_speed":""
    
}
```
- pets(uid)
```json
{
    "level":"",
    "name":"",
    "subject/type":"",
    "extra":""
}
```