

 const data = fetch('history.json').then(info =>
     info.json()).then(parsed => {

         // Create a default table
     let table = document.createElement('table');
     let thead = document.createElement('thead');
     let tbody = document.createElement('tbody');
     table.appendChild(thead);
     table.appendChild(tbody);
     document.body.appendChild(table);

     // Create heading for all the entires in json
     var firstObject = parsed[0];
     let row_1 = document.createElement('tr');
     for (const [key, value] of Object.entries(firstObject)) {
         let heading_1 = document.createElement('th');
         heading_1.innerHTML = key;
         row_1.appendChild(heading_1);
         thead.appendChild(row_1);

     }


     for (let i = 0; i < parsed.length; i++) {
         let row_2 = document.createElement('tr');

         let newRow = parsed[i];
         for (const [key, value] of Object.entries(newRow)) {
             let row_2_data_1 = document.createElement('td');
             row_2_data_1.innerHTML = value;
             row_2.appendChild(row_2_data_1);
             tbody.appendChild(row_2);

         }

     }


     for (let i = 0; i < parsed.length; i++) {
         // let text = "Commit nr " + i.toString();
         // var div = document.createElement('div');
         // document.body.appendChild(div);
         // var h1 = document.createElement('h1');
         // div.appendChild(h1);
         // h1.appendChild(document.createTextNode(parsed[0]["commit"] + " "));
         // h1.appendChild(document.createTextNode(parsed[0]["repoName"]));
     }







 })