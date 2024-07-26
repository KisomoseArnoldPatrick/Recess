<?php

namespace App\Http\Controllers;

use App\Models\Challenge;
use App\Http\Requests\StoreChallengeRequest;
use App\Http\Requests\UpdateChallengeRequest;
use Illuminate\Http\Request;

class ChallengeController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function StoreChallenge(Request $request){
        $data =new Challenge;
        $data->challengeNo= $request->challengeNo;
        $data->attemptDuration= $request->attemptDuration;
        $data->noOfQuestions= $request->noOfQuestions;
        $data->overallMark=$request->overallMark;
        $data->openDate=$request->openDate;
        $data->closeDate=$request->closeDate;
        $data->save();
        
        $notification = array(
            'message' => '',
            'alert-type' => 'success'
        );

        return redirect()->route('challenges.index')->with($notification);
    }
    public function index()
    {
        $viewchallenge = Challenge::all();
        return view('viewchallenge',compact('viewchallenge'));
        
    }

    /**
     * Show the form for creating a new resource.
     */
     public function create()
    {
        return view('challenge');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StoreChallengeRequest $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(Challenge $challenge)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Challenge $challenge)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateChallengeRequest $request, Challenge $challenge)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Challenge $challenge)
    {
        //
    }
}
