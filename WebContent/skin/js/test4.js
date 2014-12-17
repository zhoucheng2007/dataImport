/** script3 **/
if(window.ns) {
    window.ns.four = 'four';
    console.log('window.ns.four:' + window.ns.four);
    window.ns.delay(2);
} else {
    console.log('oops...');
}