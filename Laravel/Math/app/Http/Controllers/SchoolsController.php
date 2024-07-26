<?php

namespace App\Http\Controllers;

use App\Models\Schools;
use App\Http\Requests\StoreSchoolsRequest;
use App\Http\Requests\UpdateSchoolsRequest;
use Illuminate\Http\Request;

class SchoolsController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function StoreSchool(Request $request){
        $data =new Schools;
        $data->regno= $request->regno;
        $data->schoolname= $request->schoolname;
        $data->email= $request->email;
        $data->district=$request->district;
        $data->repid=$request->repid;
        $data->save();
        
        $notification = array(
            'message' => '',
            'alert-type' => 'success'
        );

        return redirect()->route('schools.index')->with($notification);
    }
    public function index()
    {
        $allschools =Schools::all();
    return view('allschools',compact('allschools'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
    return view('school');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StoreSchoolsRequest $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(Schools $schools)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Schools $schools)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateSchoolsRequest $request, Schools $schools)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Schools $schools)
    {
        //
    }
}
