Confirm = {
    show: function (message, callback) {
        Swal.fire({
            title: 'Confirm?',
            text: message,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirm!'
        }).then((result) => {
            if (result.value) {
                if (callback) {
                    callback();
                }
            }
        })
    }
}