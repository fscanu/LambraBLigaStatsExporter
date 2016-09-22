# LambraBLigaStatsExporter
This project has been built only to extract stats about teams players and coaches in the "Serie B" championship.
The idea behind the scene is to use selenium(2.5.3 with firefox 41) in order to
extract the data from the web page and export this data
in excel, or save everything in a database.
Basically the project has been divided in three layers, service, domain and repository. 
The service provide for 
the user a simple method that extract the data and export in an excel file.
The domain are simply the object that we are going to built a time that the data has been extracted. 
The repository is our source of data, in this case is selenium that is extracting the information but the idea is that can be any suitable source, ws, db and so on
