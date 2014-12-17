/** script3 **/
if(window.ns) {
    window.ns.two = 'two';
    console.log('window.ns.two:' + window.ns.two);
    window.ns.delay(2);
} else {
    console.log('oops...');
}