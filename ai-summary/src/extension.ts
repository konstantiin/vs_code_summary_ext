// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
import * as vscode from 'vscode';
import * as http from 'node:http';


// This method is called when your extension is activated
// Your extension is activated the very first time the command is executed
export function activate(context: vscode.ExtensionContext) {

	// Use the console to output diagnostic information (console.log) and errors (console.error)
	// This line of code will only be executed once when your extension is activated
	console.log('Congratulations, your extension "ai-summary" is now active!');

	function getTextToSummarize(){
    const editor = vscode.window.activeTextEditor;
    let text = null;
    const selection = editor?.selection
		if (selection && !selection.isEmpty) {
      const selectionRange = new vscode.Range(
        selection.start.line, selection.start.character, 
        selection.end.line, selection.end.character);
      text = editor.document.getText(selectionRange);
    }
    else if (selection?.isEmpty){
      text = editor?.document?.getText();
    }
    return text;
  }
  function getJsonTextToSummarize(){
    var text = getTextToSummarize();
    var model = "default"; // maybe later supported
    var data = {modelName: model, textToSummarize: text};

    return JSON.stringify(data);
  }


	let disposable = vscode.commands.registerCommand('ai-summary.summarize', (arg) => {
    console.log(arg);
    let postData = getJsonTextToSummarize();
    console.log(postData);
    if (postData === null || postData === undefined){
      vscode.window.showInformationMessage("");
      vscode.window.showErrorMessage("No text to summarize")
      return;
    }
    vscode.window.showInformationMessage("Processing your data...  \n(^◕ᴥ◕^)(^◕ᴥ◕^)")
    let req = http.request(
      {
        hostname: 'localhost',
        port: 8080,
        path: '/get-summary-service/get-summary',
        method: 'POST', 
        headers: {
             'Content-Type': 'application/json',
             'Content-Length': postData.length
           }
      },
      (res) =>{
          
          var ans = "";
          res.on('data', (chunk) => {
            console.log(`BODY: ${chunk}`);
            ans += chunk;
          });
          res.on('end', () => {
            vscode.window.showInformationMessage("Enjoy your summary! ฅ^•ﻌ•^ฅ \n")
            vscode.window.showInformationMessage(`${ans}`);
          });
        }
    );
    req.on('error', (e) => {
      console.error(e);
      vscode.window.showErrorMessage("HTTP Connection error.(っ - ‸ - ς) ")
    });
    req.write(postData);
    req.end();
    
	});

	context.subscriptions.push(disposable);
}

// This method is called when your extension is deactivated
export function deactivate() {}
