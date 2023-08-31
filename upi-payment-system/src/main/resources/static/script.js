function submitForm() {
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;
    var amount = document.getElementById("amount").value;
    var remarks = document.getElementById("remarks").value;
    

    // Send data to the backend
    fetch('/process', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id: id, name: name, amount: amount, remarks: remarks })
    })
    .then(response => response.json())
    .then(data => {
        // Handle the response from the backend
        console.log(data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
